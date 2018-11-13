package io.untaek.animal

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.Toast
import io.untaek.animal.component.PostDetailCommentListViewAdapter
import io.untaek.animal.firebase.PostInTimeline
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.activity_post_detail.view.*

class PostDetailActivity : AppCompatActivity() {

    lateinit var data: PostInTimeline

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        if(intent.getSerializableExtra("data") != null) {
            data = intent.getSerializableExtra("data") as PostInTimeline
            Toast.makeText(this, data.userName, Toast.LENGTH_SHORT).show()
        }

        post_detail_postimage.setImageResource(data.imgResource)
        post_detail_text.text = data.description
        post_detail_tag.text = data.userName

        post_detail_comment_recycler.layoutManager = GridLayoutManager(this, 1) as RecyclerView.LayoutManager?
        post_detail_comment_recycler.adapter = PostDetailCommentListViewAdapter(data.postCommentList, this)


//        FirebaseStorage.getInstance().reference
//                .child("1536092616210_gen.webm")
//                .getBytes(100000)
//                .addOnSuccessListener {
//                    Toast.makeText(applicationContext, it.size.toString(), Toast.LENGTH_SHORT).show()
//                }
//                .addOnFailureListener {
//                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
//                }




    }
}
