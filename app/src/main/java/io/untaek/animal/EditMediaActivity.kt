package io.untaek.animal

import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.MediaMetadata
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_edit_media.*

class EditMediaActivity : AppCompatActivity() {

    var thumbnails: MutableList<Bitmap> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_media)

        val path = intent.getStringExtra("path")
        //val thumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND)
        //imageView.setImageBitmap(thumbnail)
        //imageView.setImageBitmap(getThumbnails(path))
        NativeAdapter.getThumbnails(path)
    }

    fun getThumbnails(path: String): Bitmap {
        val retriever = MediaMetadataRetriever().apply {
            setDataSource(path)
        }
        val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toLong()
        val height = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_NEXT_SYNC).height
        val width = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_NEXT_SYNC).width
        val rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION).toInt()

        val length = 10

        Log.d("getThumbnails", "du: $duration")
        Log.d("getThumbnails", "he: $height")
        Log.d("getThumbnails", "wi: $width")
        Log.d("getThumbnails", "ro: $rotation")


        var w = 0
        var h = 0

        if(width > height){
            h = height
            w = height / 16 * 9
        }
        else {
            w = width
            h = width / 9 * 16
        }

        var w2 = 0

        Log.d("getThumbnails", "wi2: $w")
        Log.d("getThumbnails", "he2: $h")

        for (i in 0 until length) {
            val position: Long = (duration / 10) * i * 1000
            Log.d("getThumbnails", "$position")
            val thumbnail = retriever.getFrameAtTime(position, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
            val cropped = if (width > height) {
                Bitmap.createBitmap(thumbnail, width/2 - w/2, 0, w, h)
            } else {
                Bitmap.createBitmap(thumbnail, 0, height/2 - h/2, w, h)
            }
            thumbnails.add(cropped)
            w2 += cropped.width
        }

        val bitmap = Bitmap.createBitmap(w2, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        for (i in 0 until length) {
            canvas.drawBitmap(thumbnails[i], (w2/length*i).toFloat(), 0f, null)
        }

        return bitmap
    }
}
