package io.untaek.animal.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.DocumentSnapshot
import io.untaek.animal.R
import io.untaek.animal.TimelineDetailActivity
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Post
import kotlinx.android.synthetic.main.item_my_post.view.*
import java.lang.Exception

class MyPostRecyclerAdapter(val context: Context): RecyclerView.Adapter<MyPostRecyclerAdapter.ViewHolder>(), Fire.Callback<Pair<DocumentSnapshot?, List<Post>>> {


    override fun onResult(data: Pair<DocumentSnapshot?, List<Post>>) {
        items.addAll(data.second)
        notifyDataSetChanged()
    }

    override fun onFail(e: Exception) {
    }

    fun update() {
        Fire.getInstance().getFirstPostPage(this, 15)
    }

    private val items = arrayListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_post, parent, false), items)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        Fire.getInstance().loadThumbnail(item.content, context, holder.imageView_preview, Fire.ThumbSize.M256, RequestOptions().centerCrop().override(512), null)
        holder.textView_likes.text = item.totalLikes.toString()
    }

    class ViewHolder(itemView: View, items: ArrayList<Post>): RecyclerView.ViewHolder(itemView) {
        val imageView_preview = itemView.imageView_preview.also { iv ->
            val screenSize = Point()
            (itemView.context as Activity).windowManager.defaultDisplay.getSize(screenSize)
            iv.layoutParams = ConstraintLayout.LayoutParams(screenSize.x/3, screenSize.x/3)
            iv.setOnClickListener {
                val intent = Intent(itemView.context, TimelineDetailActivity::class.java).apply {
                    putExtra("data", items[adapterPosition])
                }
                itemView.context.startActivity(intent)
            }
        }
        val textView_likes = itemView.textView_likes
    }
}