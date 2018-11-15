package io.untaek.animal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.untaek.animal.firebase.Content
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Type
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data?.let {
            Toast.makeText(this, it.dataString, Toast.LENGTH_LONG).show()
            //Fire.getInstance().newPost(Content(Type.Video, 100, 200, "hahaha.jpg"), mapOf(), "hahahaha", it.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        button_goback.setOnClickListener {
            finish()
        }

        startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }, 10)
    }
}
