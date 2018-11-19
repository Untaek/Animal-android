package io.untaek.animal.component

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.untaek.animal.R
import io.untaek.animal.firebase.Comment2
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.User
import kotlinx.android.synthetic.main.component_timeline_detail_comment_recyclerview_header.view.*
import kotlinx.android.synthetic.main.component_timeline_detail_comment_recyclerview_item.view.*
import java.util.*


class TimelineDetailPostRecyclerViewAdapter (val post : Post, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    fun postupdate() {
        post.comments.add(Comment2("sadf", User("sfd", "asdasfd", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQitUl14tC2Ld3WVEUU0fXNoRx_oGQjgCf8QLXi-gKlbr0EJFKRFDO9OfYj"), Date(), "asdsaf"))
        post.comments.add(Comment2("sadf", User("sfd", "asdasfd", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQitUl14tC2Ld3WVEUU0fXNoRx_oGQjgCf8QLXi-gKlbr0EJFKRFDO9OfYj"), Date(), "asdsaf"))
        post.comments.add(Comment2("sadf", User("sfd", "asdasfd", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQitUl14tC2Ld3WVEUU0fXNoRx_oGQjgCf8QLXi-gKlbr0EJFKRFDO9OfYj"), Date(), "asdsaf"))
        post.comments.add(Comment2("sadf", User("sfd", "asdasfd", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQitUl14tC2Ld3WVEUU0fXNoRx_oGQjgCf8QLXi-gKlbr0EJFKRFDO9OfYj"), Date(), "asdsaf"))
        post.comments.add(Comment2("sadf", User("sfd", "asdasfd", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQitUl14tC2Ld3WVEUU0fXNoRx_oGQjgCf8QLXi-gKlbr0EJFKRFDO9OfYj"), Date(), "asdsaf"))
    }

    fun update() {
        this.postupdate()
        this.notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("ㅋㅋㅋ", "position : "+position)

        if (holder.itemViewType == 0) {
            holder as ViewHolderPost
            holder.textViewPostDecription.text = post.description
            holder.textViewPostTags.text = post.tags.values.reduce { acc, s -> acc+s }
            holder.textViewPostTimeStamp.text = timeCalculateFunction(post.timeStamp)
            Glide.with(context).load(Uri.parse(post.content.url)).apply(RequestOptions()).into(holder.imageViewPostUserIamge)
            holder.textViewUPostUserName.text = post.user.name
        } else {
            holder as ViewHolderComment
            Glide.with(context).load(Uri.parse(post.user.picture_url)).into(holder.commentImage)
//            holder.commentImage.setImageResource(R.mipmap.ic_launcher)
            //holder.commentImage.userimage_timeline_detail_comment_recyclerview_item.setImageURI(Uri.parse(post.comments[position-1].user.picture_url))
            holder.commentUserName.text = post.comments[position - 1].user.name
            holder.commentText.text = post.comments[position - 1].text

            holder.commentTime.text = timeCalculateFunction(post.comments[position - 1].timeStamp)
        }
    }

    override fun getItemCount(): Int {
       return post.comments.size+1
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0)
            return 0
        else
            return 1
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View
        if( viewType== 0){
            view = LayoutInflater.from(context).inflate(R.layout.component_timeline_detail_comment_recyclerview_header,parent,false)

            view.textView_description_timeline_item.setText(post.description)
            view.textView_user_name_timeline_detail.setText(post.user.name)
            view.textView_tags_timeline_detail.setText(post.tags.values.map { s -> "#$s " }.reduce { acc, s -> acc + s })

            return ViewHolderPost(view)
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.component_timeline_detail_comment_recyclerview_item, parent, false)
            return ViewHolderComment(view)
        }
    }


    class ViewHolderComment(view : View) : RecyclerView.ViewHolder(view) {

        val commentImage = view.userimage_timeline_detail_comment_recyclerview_item
        val commentUserName = view.username_timeline_detail_comment_recyclerview_item
        val commentText = view.text_timeline_detail_comment_recyclerview_item
        val commentTime = view.timestamp_timeline_detail_comment_recyclerview_item

        // Holds the TextView that will add each animal to
    }
    class ViewHolderPost(view : View) : RecyclerView.ViewHolder(view){
        val textViewPostDecription = view.textView_description_timeline_item
        val textViewPostTags = view.textView_tags_timeline_detail
        val textViewPostTimeStamp = view.textView_timestamp_timeline_detail
        val imageViewPostUserIamge = view.imageView_user_image_timeline_detail
        val textViewUPostUserName = view.textView_user_name_timeline_detail
    }

}

