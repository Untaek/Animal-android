package io.untaek.animal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import io.untaek.animal.component.MyCallBack
import io.untaek.animal.component.MyOnScrollListener
import io.untaek.animal.component.TimelineDetailPostRecyclerViewAdapter
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.dummy.post
import io.untaek.animal.legacy.Camera
import io.untaek.animal.util.Viewer
import kotlinx.android.synthetic.main.activity_timeline_detail.*
import kotlinx.android.synthetic.main.component_edittext_comment.*
import java.lang.Exception

class TimelineDetailActivity : AppCompatActivity(), MyCallBack , Fire.Callback<Any>{


    private var first_flag = true
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
            recyclerview_comments_timeline_detail.addOnScrollListener(MyOnScrollListener(timelineDetailPostRecyclerViewAdapter,timelineDetailPostRecyclerViewAdapter.getItems(), this))


            button_go_back_timeline_detail.setOnClickListener { finish() }

            editText2.setOnClickListener {
                Toast.makeText(this, editText.text.toString(), Toast.LENGTH_SHORT).show()
                Fire.getInstance().newComment(this, post.id, editText.text.toString(),this)
                timelineDetailPostRecyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResult(data: Any) {
        Log.d("ㅋㅋㅋ", "Comment add Success !!")
    }

    override fun onFail(e: Exception) {
        Log.d("ㅋㅋㅋ", "Comment add Fail...!")
    }
    override fun callback() {

        Log.e("ㅋㅋㅋ", "콜백")
        timelineDetailPostRecyclerViewAdapter.update()
    }

    override fun onDestroy() {
        super.onDestroy()
        //viewer.release()
    }
}
