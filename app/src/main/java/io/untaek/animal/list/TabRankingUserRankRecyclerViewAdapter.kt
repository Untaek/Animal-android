package io.untaek.animal.list

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.untaek.animal.R
import io.untaek.animal.TimelineDetailActivity
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.UserDetail
import io.untaek.animal.firebase.dummy
import io.untaek.animal.firebase.dummy.postList
import kotlinx.android.synthetic.main.component_tab_rank_userrank_recyclerview_item.view.*

class TabRankingUserRankRecyclerViewAdapter (val context: Context, val user : UserDetail ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    val postList = dummy.postList
    init {
        // user.postList로 post data들 얻어오기.
        val postList = dummy.postList
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder

        // find firebase post id : user.postList.get(0)
        // image
        Glide.with(context).load(Uri.parse(dummy.img)).apply(RequestOptions().override(100,100)).into(holder.userPostImage)

    }

    override fun getItemCount(): Int {
        return dummy.postList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(parent, postList) // postList에 얻어온 ArrayList<Post> 형식 변수 입력
    }

    class ViewHolder(parent: ViewGroup, posts : ArrayList<Post>) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.component_tab_rank_userrank_recyclerview_item,  parent, false)) {
        val userPostImage = itemView.imageView_tab_rank_userrank_item
        init {
            userPostImage.setOnClickListener {
                val intent = Intent(parent.context, TimelineDetailActivity::class.java).apply {
                    putExtra("data", posts[adapterPosition])
                }
                parent.context.startActivity(intent)
            }
        }
    }
}
