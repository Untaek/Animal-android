package io.untaek.animal.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.untaek.animal.R
import io.untaek.animal.TimelineDetailActivity
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.dummy
import io.untaek.animal.firebase.dummy.post
import kotlinx.android.synthetic.main.component_tab_rank_hotpost_recyclerview_item.view.*
import kotlinx.android.synthetic.main.component_tab_rank_recyclerview_item_userrank.view.*


class TabRankingHotPostRecyclerViewAdapter (val context: Context, val posts: List<Post> ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        Glide.with(context).load(Uri.parse(dummy.img)).apply(RequestOptions().override(100,100)).into(holder.hotPostImage)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //ViewHolder(parent);
        return ViewHolder(parent, posts)
    }

    class ViewHolder(parent: ViewGroup, posts : List<Post>) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.component_tab_rank_hotpost_recyclerview_item,  parent, false)) {
        val hotPostImage = itemView.imageView_tab_rank_hotpost_item
        init {
            hotPostImage.setOnClickListener {
                Log.e("ㅋㅋㅋ", "이동 !")
                val intent = Intent(parent.context, TimelineDetailActivity::class.java).apply {
                    putExtra("data", posts[adapterPosition])
                }
                parent.context.startActivity(intent)
            }
        }
    }
}



