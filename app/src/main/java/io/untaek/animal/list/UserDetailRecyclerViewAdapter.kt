package io.untaek.animal.list

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.net.Uri
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.DocumentSnapshot
import io.untaek.animal.R
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.UserDetail
import io.untaek.animal.firebase.dummy
import kotlinx.android.synthetic.main.component_user_detail_recyclerview_item.view.*
import java.lang.Exception

class  UserDetailRecyclerViewAdapter (val context: Context, val user : UserDetail) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Fire.Callback<Pair<DocumentSnapshot?, List<Post>>> {


    private val posts = arrayListOf<Post>()
    override fun onResult(data: Pair<DocumentSnapshot?, List<Post>>) {
        posts.addAll(data.second)
        notifyDataSetChanged()
    }

    override fun onFail(e: Exception) {
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.component_user_detail_recyclerview_item,parent,false)
       )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = posts[position]
        Log.e("ㅋㅋㅋ", "posts id : " + post.id)
        holder as ViewHolder

        Fire.getInstance().loadThumbnail(post.content, context, holder.postImage, Fire.ThumbSize.M256, RequestOptions().centerCrop().override(512), null)
        //Glide.with(context).load(Uri.parse(posts.get(position).content.url)).into(holder.postImage)

    }

    override fun getItemCount(): Int {
        return posts.size;
    }

    fun update() {
        Fire.getInstance().getFirstPostPage(this, 15)
    }

    class  ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val postImage =view.imageView_postImage_user_detail_recyclerView_item.also{iv->
            val screenSize = Point()
            (view.context as Activity).windowManager.defaultDisplay.getSize(screenSize)

            iv.layoutParams = ConstraintLayout.LayoutParams(screenSize.x/3, screenSize.x/3)
        }


    }

}

