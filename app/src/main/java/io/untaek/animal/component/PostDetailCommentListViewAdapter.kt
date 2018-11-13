package io.untaek.animal.component

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.untaek.animal.R
import io.untaek.animal.firebase.Comment
import kotlinx.android.synthetic.main.component_post_detail_comment_listview_item_.view.*
import java.security.AccessControlContext


class PostDetailCommentListViewAdapter ( val comments : List<Comment>, val context: Context) : RecyclerView.Adapter<ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.commentImage.setImageResource(comments[position].userImage)
        holder.commentImage.layoutParams.height = 200
        holder.commentImage.layoutParams.width = 200
        holder.commentUserName.text = comments[position].userName

        holder.commentText.text = comments[position].text
        holder.commentTime.text = comments[position].uploadTime
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.component_post_detail_comment_listview_item_, parent, false))
    }

}

class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val commentImage = view.post_detail_comment_listview_item_userimage
    val commentUserName = view.post_detail_comment_listview_item_username
    val commentText = view.post_detail_comment_listview_item_text
    val commentTime = view.post_detail_comment_listview_item_time
}


