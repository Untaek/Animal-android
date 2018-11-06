package io.untaek.animal.component

import android.content.Context
import android.media.MediaCodec
import android.media.MediaFormat
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.bumptech.glide.Glide
import io.untaek.animal.R
import io.untaek.animal.tab.IPost
import io.untaek.animal.tab.Post
import kotlinx.android.synthetic.main.component_timeline.view.*

class BasicTimelineAdapter(private val context: Context, private val items: MutableList<Post>) : RecyclerView.Adapter<BasicTimelineAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("holder", "")
        //holder.video.setMediaController(MediaController(context))
//        holder.video.setVideoURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/animal-f6c09.appspot.com/o/1536092616210_gen.webm?alt=media&token=db50c08d-586a-4c75-a408-08a7cf7c9be4"))
//        Glide.with(context)
//                .load("https://firebasestorage.googleapis.com/v0/b/animal-f6c09.appspot.com/o/1536092333316_gen.webp?alt=media&token=659e3ae0-8fc9-490f-8f93-71afe66a0d6f")
//                .into(holder.image)
    }


    class ViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.component_timeline,  parent, false)) {

        val description = itemView.textView_description1
        val video = itemView.surfaceView
    }
}