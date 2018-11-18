package io.untaek.animal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import io.untaek.animal.component.MyCallBack
import io.untaek.animal.component.MyOnScrollListener
import io.untaek.animal.component.TimelineDetailPostRecyclerViewAdapter
import io.untaek.animal.firebase.Post
import io.untaek.animal.util.Viewer
import kotlinx.android.synthetic.main.activity_timeline_detail.*

class TimelineDetailActivity : AppCompatActivity(), MyCallBack {
    private lateinit var post: Post
    private lateinit var viewer: Viewer
    private lateinit var timelineDetailPostRecyclerViewAdapter: TimelineDetailPostRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline_detail)


        intent.getSerializableExtra("data")?.let { d ->
            post = d as Post

            viewer = Viewer(textureView, imageView).apply {
                changeSource(post.content)
            }

            recyclerview_comments_timeline_detail.layoutManager = GridLayoutManager(this, 1)
            timelineDetailPostRecyclerViewAdapter = TimelineDetailPostRecyclerViewAdapter(post, this)

            recyclerview_comments_timeline_detail.adapter = timelineDetailPostRecyclerViewAdapter
            recyclerview_comments_timeline_detail.addOnScrollListener(MyOnScrollListener(timelineDetailPostRecyclerViewAdapter, post.comments, this))

            button_go_back.setOnClickListener { finish() }
        }
    }

    override fun callback() {
        timelineDetailPostRecyclerViewAdapter.update()
    }

    override fun onDestroy() {
        super.onDestroy()
        //viewer.release()
    }
}
