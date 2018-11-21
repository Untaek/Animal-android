package io.untaek.animal.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import com.google.android.exoplayer2.ui.PlayerView
import io.untaek.animal.firebase.Content
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Type
import java.io.File
import java.lang.Exception
import java.lang.IllegalStateException

class Viewer(val playerView: PlayerView, val imageView: ImageView) {

    private val context: Context = playerView.context

    private var bitmap: Bitmap? = null

    private var content: Content? = null

    private fun resize(content: Content) {
        val ratio = 0.6

        val p = Point()
        (context as Activity).windowManager.defaultDisplay.getSize(p)
        Log.d("Viewer", "Window size ${p.x} ${p.y}")

        val width = p.x
        val height = content.h * p.x / content.w

        if(height > p.y * 0.7) {
            playerView.layoutParams = FrameLayout.LayoutParams((width * ratio).toInt(), (height * ratio).toInt(), Gravity.CENTER)
            imageView.layoutParams = FrameLayout.LayoutParams((width * ratio).toInt(), (height * ratio).toInt(), Gravity.CENTER)
        }
        else {
            playerView.layoutParams = FrameLayout.LayoutParams(width, height, Gravity.CENTER)
            imageView.layoutParams = FrameLayout.LayoutParams(width, height, Gravity.CENTER)
        }
    }

    fun changeSource(content: Content){
        Log.d("Viewer", "changeSource")
        this.content = content
        fetch()
    }

    private fun fetch() {
        content?.let {
            resize(it)

            if(it.mime.startsWith("video")) {
                Fire.getInstance().loadMiddleThumbnail(it, context, imageView, null, null)
            }
            else {
                playerView.visibility = View.GONE
                Fire.getInstance().loadActualContent(it, context, object: Fire.Callback<Any> {
                    override fun onResult(data: Any) {
                        Log.d("Post", data.toString())
                        imageView.visibility = View.VISIBLE
                        bitmap = data as Bitmap
                        imageView.setImageBitmap(bitmap)
                        Log.d("Bitmap Size", "${bitmap?.width} ${bitmap?.height}")
                    }

                    override fun onFail(e: Exception) {
                        Log.w("Post", "error", e)
                    }
                })
            }
        }

    }
}