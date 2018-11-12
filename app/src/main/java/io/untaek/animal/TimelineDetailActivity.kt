package io.untaek.animal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import io.untaek.animal.firebase.PostInTimeline
import kotlinx.android.synthetic.main.activity_timeline_detail.*

class TimelineDetailActivity : AppCompatActivity() {

    lateinit var data: PostInTimeline

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline_detail)

        if(intent.getSerializableExtra("data") != null) {
            data = intent.getSerializableExtra("data") as PostInTimeline
            Toast.makeText(this, data.userName, Toast.LENGTH_SHORT).show()
        }

        textView_description.text = data.description
        textView_user_name.text = data.userName

        FirebaseStorage.getInstance().reference
                .child("1536092616210_gen.webm")
                .getBytes(100000)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, it.size.toString(), Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
    }
}
