package io.untaek.animal.list

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.DocumentSnapshot
import io.untaek.animal.R
import io.untaek.animal.firebase.Comment2
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.User
import kotlinx.android.synthetic.main.component_timeline_detail_comment_recyclerview_header.view.*
import kotlinx.android.synthetic.main.component_timeline_detail_comment_recyclerview_item.view.*
import java.lang.Exception
import java.util.*


class TimelineDetailPostRecyclerViewAdapter (val post : Post, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Fire. Callback<Pair<DocumentSnapshot?, List<Comment2?>>>  {

    init {
        Fire.getInstance().firstreadComments(post.id, this)
        Log.e("ㅋㅋㅋ", "생성자에서 firstreadComments")
    }
    val comments: ArrayList<Comment2?> = arrayListOf()
    val uploader = User("dbsdlswp", "inje", "https://s-i.huffpost.com/gen/4479784/images/n-THRO-628x314.jpg")
    private var lastSeen: DocumentSnapshot? = null
    private var lastSeen_before : DocumentSnapshot? = null
    var addDataFlag : Boolean = false

    fun getItems() = comments

    override fun onFail(e: Exception) {
    }

    override fun onResult(data: Pair<DocumentSnapshot?, List<Comment2?>>) {
        Log.e("ㅋㅋㅋ", "on result")
        if(data.first != null) {
            if(addDataFlag) {
                comments.removeAt(comments.size - 1)
                addDataFlag = false
            }
            comments.addAll(data.second)
            lastSeen = data.first!!
            notifyDataSetChanged()
        }else{
            Log.e("ㅋㅋㅋ", "data == null")
        }
    }
    fun update() {
        Log.e("ㅋㅋㅋ", "update")
        if(lastSeen != lastSeen_before && lastSeen != null) {
            Fire.getInstance().readComments(post.id, lastSeen!!, this)
            lastSeen_before = lastSeen
            lastSeen = null
        }
    }

    fun addData(postId : String, commentText : String){
        Log.e("ㅋㅋㅋ", "addData")
        comments.add( 0,Comment2(postId, uploader, Date(), commentText ))
        //comments.add(Comment2(postId, uploader, Date(), commentText ))
        addDataFlag = true
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("ㅋㅋㅋ", "position : "+position)

        if (holder.itemViewType == 0) {
            holder as ViewHolderPost
            holder.textViewPostDecription.text = post.description
            holder.textViewPostTags.text = if (post.tags.isNotEmpty()) post.tags.values.map { s -> "#$s " }.reduce { acc, s -> acc + s } else ""
            holder.textViewPostTimeStamp.text = timeCalculateFunction(post.timeStamp)
            Glide.with(context).load(Uri.parse(post.content.url)).apply(RequestOptions()).into(holder.imageViewPostUserIamge)
            holder.textViewUPostUserName.text = post.user.name
        } else {
            holder as ViewHolderComment
            Glide.with(context).load(Uri.parse(comments[position-1]!!.user.pictureUrl)).into(holder.commentImage)
            holder.commentUserName.text = comments[position-1]!!.user.name
            holder.commentText.text = comments[position-1]!!.commentText
            holder.commentTime.text = timeCalculateFunction(comments[position-1]!!.timeStamp)
        }
    }

    override fun getItemCount(): Int {
       return comments.size+1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            0
        else
            1
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
        val textViewPostDecription = view.textView_description_timeline_detail_header
        val textViewPostTags = view.textView_tags_timeline_detail_header
        val textViewPostTimeStamp = view.textView_timestamp_timeline_detail_header
        val imageViewPostUserIamge = view.imageView_user_image_timeline_detail_header
        val textViewUPostUserName = view.textView_user_name_timeline_detail_header
    }

}

