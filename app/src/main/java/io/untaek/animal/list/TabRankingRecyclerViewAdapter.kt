package io.untaek.animal.list

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import io.untaek.animal.R
import io.untaek.animal.UserDetailActivity
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.UserDetail
import io.untaek.animal.firebase.dummy
import io.untaek.animal.firebase.dummy.img
import kotlinx.android.synthetic.main.component_tab_rank_recyclerview_item_hotpost.view.*
import kotlinx.android.synthetic.main.component_tab_rank_recyclerview_item_userrank.view.*
import java.lang.Exception

class  TabRankingRecyclerViewAdapter (private val context: Context, val users: ArrayList<UserDetail>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if( viewType== 0){
            return ViewHolderHotPost(parent)
        }else {
            return ViewHolderUserRank(parent, users)
        }
    }

    override fun getItemCount(): Int {
        return users.size+1
    }

    fun update(){
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            holder as ViewHolderHotPost

            holder.hotPostLabel.text = "Hot Post"
            holder.hotPostRecyclerView.layoutManager = GridLayoutManager(context, 3)

            holder.hotPostRecyclerView.adapter = TabRankingHotPostRecyclerViewAdapter(context).also {
                it.update()
            }
            holder.userRankLabel.text = "User Rank"
        } else {
            val user = users.get(position-1)
            holder as ViewHolderUserRank
            holder.userImage
            holder.userImage.clipToOutline = true
            holder.userImage.background = ShapeDrawable(OvalShape())
            Glide.with(context).load(Uri.parse(user.pictureUrl)).into(holder.userImage)
            holder.userName.text = user.userName
            holder.userFollowCount.text = user.totalFollowers.toString()
            holder.userLikeCount.text =user.totalLikes.toString()

            holder.userPostRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            holder.userPostRecyclerView.adapter = TabRankingUserRankRecyclerViewAdapter(context, users.get(position - 1)).also {

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0)
            return 0
        else
            return 1
    }
    class ViewHolderHotPost(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.component_tab_rank_recyclerview_item_hotpost,parent,false)) {
        val hotPostLabel:TextView = itemView.textView_hotpost_label_tab_rank
        val hotPostRecyclerView:RecyclerView = itemView.recyclerView_hotPost_tab_rank_hotPost
        val userRankLabel :TextView= itemView.textView_userRank_label_tab_rank
        val context: Context = parent.context
        init {
        }
    }
    class ViewHolderUserRank(parent: ViewGroup, users : ArrayList<UserDetail>) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.component_tab_rank_recyclerview_item_userrank, parent, false)){
        val userImage : ImageView = itemView.imageView_user_image_tab_rank_userRank
        val userName : TextView = itemView.textView_user_name_tab_rank_userRank
        val userFollowCount:TextView = itemView.textView_follow_count_tab_rank_userRank
        val userLikeCount :TextView =itemView. textView_like_count_tab_rank_userRank
        val userPostRecyclerView:RecyclerView = itemView.recyclerView_user_post_tab_rank_userRank

        val context : Context = parent.context

        init {
            userName.setOnClickListener {
                val intent = Intent(context, UserDetailActivity::class.java).apply {
                    putExtra("data", users[adapterPosition-1])
                }
                context.startActivity(intent)
            }
        }
    }
}


