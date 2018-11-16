package io.untaek.animal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.untaek.animal.component.MyCallBack
import io.untaek.animal.component.MyOnScrollListener
import io.untaek.animal.component.TimelineDetailCommentRecyclerViewAdapter
import io.untaek.animal.component.Viewer
import io.untaek.animal.firebase.Post
import kotlinx.android.synthetic.main.activity_timeline_detail.*

class TimelineDetailActivity : AppCompatActivity(), MyCallBack {
    private lateinit var post: Post
    private lateinit var viewer: Viewer
    private lateinit var timelineDetailCommentRecyclerViewAdapter: TimelineDetailCommentRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline_detail)


        intent.getSerializableExtra("data")?.let { d ->
            post = d as Post

            viewer = Viewer(textureView, imageView).apply {
                changeSource(post.content)
            }

            recyclerview_timeline_detail.layoutManager = GridLayoutManager(this, 1)
            timelineDetailCommentRecyclerViewAdapter = TimelineDetailCommentRecyclerViewAdapter(post, this)

            recyclerview_timeline_detail.adapter = timelineDetailCommentRecyclerViewAdapter
            recyclerview_timeline_detail.addOnScrollListener(MyOnScrollListener(timelineDetailCommentRecyclerViewAdapter, post.comments, this))

            button_go_back.setOnClickListener { finish() }
        }
    }

    override fun callback() {
        timelineDetailCommentRecyclerViewAdapter.update()
    }

    override fun onDestroy() {
        super.onDestroy()
        //viewer.release()
    }
}
