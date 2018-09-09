package io.untaek.animal

import android.media.ThumbnailUtils
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_edit_media.*

class EditMediaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_media)

        val path = intent.getStringExtra("path")
        val thumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND)
        imageView.setImageBitmap(thumbnail)
    }
}
