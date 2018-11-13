package io.untaek.animal

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import io.untaek.animal.component.Viewer
import io.untaek.animal.firebase.PostInTimeline
import kotlinx.android.synthetic.main.activity_timeline_detail.*
import java.io.File

class TimelineDetailActivity : AppCompatActivity() {

    lateinit var data: PostInTimeline
    lateinit var viewer: Viewer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline_detail)

        if(intent.getSerializableExtra("data") != null) {
            data = intent.getSerializableExtra("data") as PostInTimeline
        }

        textView_description.text = data.description
        textView_user_name.text = data.userName
        viewer = Viewer(surfaceView)
    }
}
