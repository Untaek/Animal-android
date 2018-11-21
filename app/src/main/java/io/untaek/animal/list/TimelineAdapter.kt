package io.untaek.animal.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ui.PlayerView
import com.google.firebase.firestore.DocumentSnapshot
import im.ene.toro.ToroPlayer
import im.ene.toro.ToroUtil
import im.ene.toro.exoplayer.ExoPlayerViewHelper
import im.ene.toro.exoplayer.Playable
import im.ene.toro.media.PlaybackInfo
import im.ene.toro.widget.Container
import io.untaek.animal.R
import io.untaek.animal.TimelineDetailActivity
import io.untaek.animal.UserDetailActivity
import io.untaek.animal.firebase.Content
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Post
import io.untaek.animal.util.Viewer
import kotlinx.android.synthetic.main.item_timeline.view.*
import java.io.File
import java.lang.Exception

class TimelineAdapter(private val context: Context) : RecyclerView.Adapter<TimelineAdapter.BaseViewHolder>(), Fire.Callback<Pair<DocumentSnapshot?, List<Post>>> {

    companion object {
        const val IMAGE = 0
        const val VIDEO = 1
    }

    private val items: ArrayList<Post> = arrayListOf()
    private lateinit var lastSeen: DocumentSnapshot
    private var loading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        if(viewType == IMAGE) {
            return BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_timeline_image,  parent, false), items)
        }else if (viewType == VIDEO) {
            return VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_timeline,  parent, false), items)
        }
        throw Exception()
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        holder.onRecycled()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(items[position].content.mime.startsWith("image")) IMAGE else VIDEO
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateList() {
        if(!loading){
            loading = !loading
            if(items.size == 0){
                Fire.getInstance().getFirstPostPage(this)
            }
            else {
                Fire.getInstance().getPostPage(lastSeen,this)
            }
        }
    }

    fun getItems() = items

    override fun onResult(data: Pair<DocumentSnapshot?, List<Post>>) {
        Log.d("TimelineAdapter", "onResult ${data.second.size}")
        loading = !loading
        if (data.first != null) {
            lastSeen = data.first!!
        }
        items.addAll(data.second)
        notifyDataSetChanged()
    }

    override fun onFail(e: Exception) {

    }

    open class BaseViewHolder(itemView: View, val items: ArrayList<Post>): RecyclerView.ViewHolder(itemView) {
        val context = itemView.context

        val description: TextView = itemView.textView_description
        val user_name: TextView = itemView.textView_name
        val pet_name: TextView = itemView.textView_pet_name
        val likes: TextView = itemView.textView_like
        val container: FrameLayout = itemView.frame_image_container
        val imageView: ImageView = itemView.imageView
        val imageView_user_picture: ImageView = itemView.imageView_user_image
        val imageView_like: ImageView = itemView.imageView_like

        init {
            imageView_user_picture.clipToOutline = true
            imageView_user_picture.background = ShapeDrawable(OvalShape())

            description.setOnClickListener {
                val intent = Intent(context, TimelineDetailActivity::class.java).apply {
                    putExtra("data", items[adapterPosition])
                }
                context.startActivity(intent)
            }

            user_name.setOnClickListener {
                val intent = Intent(context, UserDetailActivity::class.java).apply {
                    putExtra("data", items[adapterPosition])
                }
                context.startActivity(intent)
            }

            pet_name.setOnClickListener {
                val intent = Intent(context, TimelineDetailActivity::class.java).apply {
                    putExtra("data", items[adapterPosition])
                }
                context.startActivity(intent)
            }

            likes.setOnClickListener {
                Fire.getInstance().toggleLike(items[adapterPosition].like, items[adapterPosition].id, items[adapterPosition].user.id, likeButtonClickCallback)
            }

            imageView_like.setOnClickListener {
                Fire.getInstance().toggleLike(items[adapterPosition].like, items[adapterPosition].id, items[adapterPosition].user.id, likeButtonClickCallback)
            }
        }

        fun resize(content: Content, view: View) {
            val ratio = 0.6

            val p = Point()
            (context as Activity).windowManager.defaultDisplay.getSize(p)
            Log.d("Viewer", "Window size ${p.x} ${p.y}")

            val width = p.x
            val height = content.h * p.x / content.w

            if(height > p.y * 0.7) {
                view.layoutParams = FrameLayout.LayoutParams((width * ratio).toInt(), (height * ratio).toInt(), Gravity.CENTER)
            }
            else {
                view.layoutParams = FrameLayout.LayoutParams(width, height, Gravity.CENTER)
            }
        }

        open fun bind(item: Post) {
            description.text = item.description
            user_name.text = item.user.name
            pet_name.text = this.javaClass.name
            likes.text = item.totalLikes.toString()
            imageView_like.setImageResource(
                    if (item.like)
                        R.drawable.ic_favorite_black_24dp
                    else
                        R.drawable.ic_favorite_border_black_24dp
            )
            Glide.with(context)
                    .load(item.user.pictureUrl)
                    .into(imageView_user_picture)

            resize(item.content, imageView)

            //if(item.content.mime.startsWith("image"))
                    Fire.getInstance().loadThumbnail(item.content, context, imageView, Fire.ThumbSize.M256, null, null)
        }

        private val likeButtonClickCallback = object : Fire.Callback<Pair<Boolean, Long>> {
            override fun onResult(data: Pair<Boolean, Long>) {
                when(data.first) {
                    true -> imageView_like.setImageResource(R.drawable.ic_favorite_black_24dp)
                    false -> imageView_like.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                }
                items[adapterPosition].like = data.first
                items[adapterPosition].totalLikes = data.second
            }

            override fun onFail(e: Exception) {
                Log.d("TimelineAdapter", "like error", e)
            }
        }

        open fun onRecycled(){

        }
    }

    class VideoViewHolder(itemView: View, items: ArrayList<Post>): BaseViewHolder(itemView, items), ToroPlayer {

        var helper: ExoPlayerViewHelper? = null
        var uri: Uri? = null

        private val TAG = "TimelineAdapter"

        val playerView: PlayerView = itemView.playerView
        val image_play: ImageView = itemView.imageView_play
        val progressBar: ProgressBar = itemView.progress_bar

        override fun bind(item: Post) {
            super.bind(item)
            resize(item.content, playerView)
            uri = null
            Fire.getInstance().loadVideoDownloadUri(item.content, object : Fire.Callback<Uri>{
                override fun onResult(data: Uri) {
                    uri = data

                }

                override fun onFail(e: Exception) {

                }

            })
        }

        private val listener = object : Playable.DefaultEventListener(){
            override fun onLoadingChanged(isLoading: Boolean) {
                if(!isLoading) {
                    progressBar.visibility = View.GONE
                    imageView.visibility = View.GONE
                    image_play.visibility = View.GONE
                }
                else {
                    image_play.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    imageView.visibility = View.VISIBLE
                }
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if(!playWhenReady) {
                    image_play.visibility = View.VISIBLE
                    imageView.visibility = View.VISIBLE
                }
                else{
                    imageView.visibility = View.GONE
                }
            }
        }

        override fun isPlaying(): Boolean {
            return helper != null && helper!!.isPlaying
        }

        override fun getPlayerView(): View {
            return playerView
        }

        override fun pause() {
            if(helper != null) helper!!.pause()
        }

        override fun wantsToPlay(): Boolean {
            return ToroUtil.visibleAreaOffset(this, itemView.parent) >= 0.85
        }

        override fun play() {
            if(helper != null) helper!!.play()
        }

        override fun getCurrentPlaybackInfo(): PlaybackInfo {
            return if (helper != null) helper!!.latestPlaybackInfo else PlaybackInfo()
        }

        override fun release() {
            if (helper != null) {
                helper!!.removeEventListener(listener)
                helper!!.release()
                helper = null
            }
        }

        override fun initialize(container: Container, playbackInfo: PlaybackInfo) {
            if(uri != null){
                if(helper == null) {
                    helper = ExoPlayerViewHelper(this, uri!!).apply {
                        addEventListener(listener)
                    }
                }
                helper!!.initialize(container, playbackInfo)
            }

        }

        override fun getPlayerOrder(): Int {
            return adapterPosition
        }

        override fun onRecycled() {

        }
    }
}