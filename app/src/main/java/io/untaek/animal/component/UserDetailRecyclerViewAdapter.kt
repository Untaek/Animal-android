package io.untaek.animal.component

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.untaek.animal.R
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.UserDetail
import io.untaek.animal.firebase.dummy
import kotlinx.android.synthetic.main.component_user_detail_recyclerview_item.view.*

class  UserDetailRecyclerViewAdapter (val context: Context, val user : UserDetail) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val posts : List<Post> = dummy.postList
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("ㅋㅋㅋ", "position : " + position)
        holder as ViewHolder
        Glide.with(context).load(Uri.parse(posts.get(position).content.url)).into(holder.postImage)
    }
    override fun getItemCount(): Int {
        return user.postList.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View
        view = LayoutInflater.from(context).inflate(R.layout.component_user_detail_recyclerview_item,parent,false)
        return ViewHolder(view)
    }

    class  ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val postImage =view.imageView_postImage_user_detail_recyclerView_item
    }

}

