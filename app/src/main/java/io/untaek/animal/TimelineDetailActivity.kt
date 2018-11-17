package io.untaek.animal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.untaek.animal.util.Viewer
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

            textView_description.text = post.description
            textView_user_name.text = post.user.name
            textView_tags.text = if (post.tags.isNotEmpty())
                post.tags.values.map { s -> "#$s " }.reduce { acc, s -> acc + s }
            else ""

            button_go_back.setOnClickListener { finish() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //viewer.release()
    }
}
