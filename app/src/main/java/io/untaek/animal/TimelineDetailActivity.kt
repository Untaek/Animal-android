package io.untaek.animal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.untaek.animal.component.Viewer
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.PostInTimeline
import io.untaek.animal.firebase.dummy
import kotlinx.android.synthetic.main.activity_timeline_detail.*
import java.lang.Exception

class TimelineDetailActivity : AppCompatActivity() {

    private lateinit var data: PostInTimeline
    private lateinit var viewer: Viewer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline_detail)

        intent.getSerializableExtra("data")?.let { d ->
            data = d as PostInTimeline

            Fire.getInstance().postDetail("XpDIlqSeTpejTLcjsDAh", object: Fire.Callback {
                override fun onResult(data: Any) {
                    val post = data as Post
                    textView_description.text = post.description
                    textView_user_name.text = post.user.name
                    Log.d("Post", data.toString())

                    Fire.getInstance().loadActualContent(post.content, applicationContext, object: Fire.Callback {
                        override fun onResult(data: Any) {
                            Log.d("Post", data.toString())
                        }

                        override fun onFail(e: Exception) {
                            Log.w("Post", "error", e)
                        }
                    })
                }

                override fun onFail(e: Exception) {
                }

            })
            textView_description.text = data.description
            textView_user_name.text = data.userName

            viewer = Viewer(surfaceView).apply {
                sourceChange(dummy.post.content)
            }

            button_go_back.setOnClickListener { finish() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewer.release()
    }
}
