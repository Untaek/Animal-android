package io.untaek.animal.component

import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import io.untaek.animal.R
import io.untaek.animal.UserDetailActivity
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.UserDetail
import io.untaek.animal.firebase.dummy
import io.untaek.animal.firebase.dummy.img
import kotlinx.android.synthetic.main.component_tab_rank_recyclerview_item_hotpost.view.*
import kotlinx.android.synthetic.main.component_tab_rank_recyclerview_item_userrank.view.*

class  TabRankingRecyclerViewAdapter (private val context: Context, val posts : ArrayList<Post>, val users: ArrayList<UserDetail> ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    fun userUpadate(){
        users.add(UserDetail("AAA","inje", dummy.img,7421,24,300))
        users.add(UserDetail("BBB","untaek",img,3333,14,255))
        users.add(UserDetail("CCC","jonghyun", dummy.img,2224,11,226))
        users.add(UserDetail("DDD","taeyang", dummy.img,366,2,44))
        users.add(UserDetail("EEE","sungbum", dummy.img,442,5,31))
    }

    fun update(){
        userUpadate()
        notifyDataSetChanged()
    }

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            holder as ViewHolderHotPost
            holder.hotPostLabel.text = "Hot Post"
            holder.hotPostRecyclerView.layoutManager = GridLayoutManager(context, 3)
            holder.hotPostRecyclerView.adapter = TabRankingHotPostRecyclerViewAdapter(context, posts)
            holder.userRankLabel.text = "User Rank"
        } else {
            val user = users.get(position-1)
            holder as ViewHolderUserRank
            holder.userName.text = user.userName
            holder.userFollowCount.text = user.totalFollows.toString()
            holder.userLikeCount.text =user.totalLikes.toString()

            holder.userPostRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            holder.userPostRecyclerView.adapter =  TabRankingUserRankRecyclerViewAdapter(context, users.get(position-1))
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
    class ViewHolderUserRank(parent: ViewGroup, users : ArrayList<UserDetail>) :
            RecyclerView.ViewHolder(LayoutInflater.from(parent.context).
                    inflate(R.layout.component_tab_rank_recyclerview_item_userrank, parent, false)){
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


