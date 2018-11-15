package io.untaek.animal.component

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.untaek.animal.R
import io.untaek.animal.R.id.*
import io.untaek.animal.firebase.Post
import kotlinx.android.synthetic.main.component_post_detail_comment_listview_item_.view.*
import kotlinx.android.synthetic.main.component_timeline_detail_comment_recyclerview_header.view.*


class PostDetailCommentListViewAdapter (val post : Post, val context: Context) : RecyclerView.Adapter<ViewHolder>(){
    var headerFlag : Boolean = false;

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.commentImage.setImageURI(Uri.parse(post.comments[position].user.picture_url))
        holder.commentImage.layoutParams.height = 200
        holder.commentImage.layoutParams.width = 200
        holder.commentUserName.text = post.comments[position].user.name

        holder.commentText.text = post.comments[position].text
        holder.commentTime.text = post.comments[position].timeStamp.toString()
    }

    override fun getItemCount(): Int {
        return post.comments.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == 0){
            val view : View
            view = LayoutInflater.from(context).inflate(R.layout.component_timeline_detail_comment_recyclerview_header,parent,false)
            view.textView_description_timeline_detail.setText(post.description)
            view.textView_user_name_timeline_detail.setText(post.user.name)
            view.textView_tags_timeline_detail.setText(post.tags.values.map { s -> "#$s " }.reduce { acc, s -> acc + s })
            headerFlag = true

            return ViewHolder(view)
        }else {
            headerFlag = false;
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.component_post_detail_comment_listview_item_, parent, false))
        }
    }

}

class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val commentImage = view.post_detail_comment_listview_item_userimage
    val commentUserName = view.post_detail_comment_listview_item_username
    val commentText = view.post_detail_comment_listview_item_text
    val commentTime = view.post_detail_comment_listview_item_time
}


