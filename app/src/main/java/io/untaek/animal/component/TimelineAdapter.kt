package io.untaek.animal.component

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.firestore.DocumentSnapshot
import io.untaek.animal.R
import io.untaek.animal.TimelineDetailActivity
import io.untaek.animal.UserDetailActivity
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Post
import io.untaek.animal.util.Viewer
import kotlinx.android.synthetic.main.item_timeline.view.*
import java.lang.Exception

class TimelineAdapter(private val context: Context) : RecyclerView.Adapter<TimelineAdapter.ViewHolder>(), Fire.Callback<Pair<DocumentSnapshot?, List<Post>>> {

    private val items: MutableList<Post> = mutableListOf()
    private lateinit var lastSeen: DocumentSnapshot
    private var loading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("TimelineAdapter", "viewType: $viewType")
        return ViewHolder(parent, items)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("holder", "")

        val item = items[position]

        holder.description.text = item.description
        holder.user_name.text = item.user.name
        holder.pet_name.text = "dog"
        holder.likes.text = item.totalLikes.toString()
        holder.viewer.changeSource(item.content)
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

    override fun onResult(data: Pair<DocumentSnapshot?, List<Post>>) {
        loading = !loading
        if (data.first != null) {
            lastSeen = data.first!!
        }
        items.addAll(data.second)
        notifyDataSetChanged()
    }

    override fun onFail(e: Exception) {

    }

    class ViewHolder(parent: ViewGroup, items: MutableList<Post>): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_timeline,  parent, false)) {
        val description: TextView = itemView.textView_description_timeline_detail_header
        val textureView: TextureView = itemView.textureView
        val user_name: TextView = itemView.textView_name
        val pet_name: TextView = itemView.textView_pet_name
        val likes: TextView = itemView.textView_like
        val container: FrameLayout = itemView.frame_image_container
        val imageView: ImageView = itemView.imageView
        val viewer: Viewer = Viewer(textureView, imageView)

        val context: Context = parent.context

        init {
            description.setOnClickListener {
                val intent = Intent(context, TimelineDetailActivity::class.java).apply {
                    putExtra("data", items[adapterPosition])
                }
                context.startActivity(intent)
            }

            user_name.setOnClickListener {
                val intent = Intent(context, UserDetailActivity::class.java).apply {

                }
            }

            pet_name.setOnClickListener {

            }

            likes.setOnClickListener {

            }
        }
    }
}