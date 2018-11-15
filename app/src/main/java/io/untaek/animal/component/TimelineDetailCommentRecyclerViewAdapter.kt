package io.untaek.animal.component

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.untaek.animal.R
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.dummy.post
import kotlinx.android.synthetic.main.component_timeline_detail_comment_recyclerview_header.view.*
import kotlinx.android.synthetic.main.component_timeline_detail_comment_recyclerview_item.view.*
import java.sql.Timestamp
import java.util.*


class TimelineDetailCommentRecyclerViewAdapter (val post : Post, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.headerFlag == true) {

        } else {
            bodyBindInit(holder, position)
        }
    }

    var headerFlag = true

    fun bodyBindInit(holder: ViewHolder, position: Int) {
        holder.commentImage.layoutParams.height = 120
        holder.commentImage.layoutParams.width = 120
        holder.commentImage.setImageResource(R.mipmap.ic_launcher)
        //holder.commentImage.userimage_timeline_detail_comment_recyclerview_item.setImageURI(Uri.parse(post.comments[position-1].user.picture_url))
        holder.commentUserName.username_timeline_detail_comment_recyclerview_item.text = post.comments[position - 1].user.name
        holder.commentText.text = post.comments[position - 1].text

        holder.commentTime.text = timeCalculateFunction(post.comments[position - 1].timeStamp)
        //holder.commentTime.text = post.comments[position-1].timeStamp.toString()
    }

    override fun getItemCount(): Int {
        return post.comments.size + 1
    }

    fun timeCalculateFunction(time: Date): String {
        val result: String
        val gc = GregorianCalendar(TimeZone.getTimeZone("Asia/Soul"))
        val gc_year = gc.get(GregorianCalendar.YEAR)
        val gc_month = gc.get(GregorianCalendar.MONTH)
        val gc_date = gc.get(GregorianCalendar.DATE)
        val gc_hour = gc.get(GregorianCalendar.HOUR)
        val gc_minute = gc.get(GregorianCalendar.MINUTE)

        val time_year = time.year
        val time_month = time.month
        val time_date = time.date
        val time_hour = time.hours
        val time_minute = time.minutes

        if (time_year - gc_year > 0) {
            result = "" + (time_year - gc_year).toString() + "년 전"
        } else if (time_month - gc_month > 0) {
            result = "" + (time_month - gc_month) + "달 전"
        } else if (time_date - gc_date > 0) {
            result = "" + (time_date - gc_date) + "일 전"
        } else if (time_hour - gc_hour > 0) {
            result = "" + (time_hour - gc_hour) + "시간 전"
        } else if (time_minute - gc_minute > 0) {
            result = "" + (time_minute - gc_minute) + "분 전"
        }else{
            result = "몇초 전 "
        }
        return result
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View
        if( headerFlag== true){
            view = LayoutInflater.from(context).inflate(R.layout.component_timeline_detail_comment_recyclerview_header,parent,false)
            view.textView_description_timeline_detail.setText(post.description)
            view.textView_user_name_timeline_detail.setText(post.user.name)
            view.textView_tags_timeline_detail.setText(post.tags.values.map { s -> "#$s " }.reduce { acc, s -> acc + s })
            headerFlag = false
            return ViewHolder(view, true)
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.component_timeline_detail_comment_recyclerview_item, parent, false)
            return ViewHolder(view, false)
        }
    }
}



class ViewHolder (view : View, headerFlag : Boolean) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val commentImage = view.userimage_timeline_detail_comment_recyclerview_item
    val commentUserName = view.username_timeline_detail_comment_recyclerview_item
    val commentText = view.text_timeline_detail_comment_recyclerview_item
    val commentTime = view.timestamp_timeline_detail_comment_recyclerview_item
    val headerFlag = headerFlag
}

