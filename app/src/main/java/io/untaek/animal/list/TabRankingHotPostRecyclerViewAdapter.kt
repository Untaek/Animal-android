package io.untaek.animal.list

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.DocumentSnapshot
import io.untaek.animal.R
import io.untaek.animal.TimelineDetailActivity
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.dummy
import io.untaek.animal.firebase.dummy.post
import kotlinx.android.synthetic.main.component_tab_rank_hotpost_recyclerview_item.view.*
import java.lang.Exception

class TabRankingHotPostRecyclerViewAdapter (val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Fire.Callback<Pair<DocumentSnapshot?, List<Post>>> {


    override fun onResult(data: Pair<DocumentSnapshot?, List<Post>>) {
        posts.addAll(data.second)
        notifyDataSetChanged()
    }

    override fun onFail(e: Exception) {

    }

    fun update(){
        Fire.getInstance().getHotPost(this)
    }

    val posts = arrayListOf<Post>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        Log.e("ㅋㅋㅋ", "hotpost - onCreateViewHolder ")
        //ViewHolder(parent);
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.component_tab_rank_hotpost_recyclerview_item, parent, false), posts)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = posts[position]

        Log.e("ㅋㅋㅋ", "hotpost - post.content.url : " + post.content.url)
        holder as ViewHolder
        Glide.with(context).load(Uri.parse(post.content.url)).apply(RequestOptions().override(100,100)).into(holder.hotPostImage)
        holder.hotPostTextCount.text =post.totalLikes.toString()
    }

    override fun getItemCount(): Int {
        return posts.size
    }



    class ViewHolder(itemView: View, items : ArrayList<Post>) : RecyclerView.ViewHolder(itemView) {
        val hotPostImage = itemView.imageView_tab_rank_hotpost_item
        val hotPostTextCount = itemView.textView_count_tab_rank_hotpost_item
        init {
            hotPostImage.setOnClickListener {
                Log.e("ㅋㅋㅋ", "이동 !")
                val intent = Intent(itemView.context, TimelineDetailActivity::class.java).apply {
                    putExtra("data", items[adapterPosition])
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}



