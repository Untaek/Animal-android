package io.untaek.animal.component

import android.content.Context
import android.content.Intent
import android.media.MediaCodec
import android.media.MediaFormat
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.storage.FirebaseStorage
import io.untaek.animal.R
import io.untaek.animal.TimelineDetailActivity
import io.untaek.animal.UserDetailActivity
import io.untaek.animal.firebase.PostInTimeline
import io.untaek.animal.firebase.dummy
import kotlinx.android.synthetic.main.item_timeline.view.*
import java.io.File

class BasicTimelineAdapter(private val context: Context) : RecyclerView.Adapter<BasicTimelineAdapter.ViewHolder>() {
    var items: ArrayList<PostInTimeline>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent, items!!)

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("holder", "")

        val item = items!![position]

        holder.description.text = item.description
        holder.user_name.text = item.userName
        holder.pet_name.text = item.petName
        holder.likes.text = item.likes.toString()
    }

    fun updateList() {
        items = dummy.timelines
        notifyDataSetChanged()
    }

    class ViewHolder(parent: ViewGroup, items: ArrayList<PostInTimeline>): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_timeline,  parent, false)) {
        val description: TextView = itemView.textView_description
        val surfaceView: SurfaceView = itemView.surfaceView
        val user_name: TextView = itemView.textView_name
        val pet_name: TextView = itemView.textView_pet_name
        val likes: TextView = itemView.textView_like
        val container: FrameLayout = itemView.frame_image_container
        val viewer: Viewer = Viewer(surfaceView)

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