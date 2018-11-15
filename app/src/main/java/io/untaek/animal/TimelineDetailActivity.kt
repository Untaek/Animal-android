package io.untaek.animal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import io.untaek.animal.component.TimelineDetailCommentRecyclerViewAdapter
import io.untaek.animal.component.Viewer
import io.untaek.animal.firebase.Post
import kotlinx.android.synthetic.main.activity_timeline_detail.*

class TimelineDetailActivity : AppCompatActivity() {
    private lateinit var post: Post
    private lateinit var viewer: Viewer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline_detail)

        intent.getSerializableExtra("data")?.let { d ->
            post = d as Post

            viewer = Viewer(textureView, imageView).apply {
                changeSource(post.content)
            }



            recyclerView_comments.layoutManager = GridLayoutManager(this, 1)
            recyclerView_comments.adapter = TimelineDetailCommentRecyclerViewAdapter(post, this)



            button_go_back.setOnClickListener { finish() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //viewer.release()
    }
}
