package io.untaek.animal.component

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import io.untaek.animal.firebase.Content
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Type
import java.io.File

class Viewer(val surfaceView: SurfaceView) : SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    private val context: Context = surfaceView.context
    private val mediaPlayer: MediaPlayer = MediaPlayer().also {
        it.setOnPreparedListener(this)
    }
    private val surfaceHolder: SurfaceHolder = surfaceView.holder.also {
        it.addCallback(this)
    }

    fun sourceChange(content: Content){
        surfaceHolder.setFixedSize(content.w, content.h)

        if (content.type == Type.Image) {

        }
        else if (content.type == Type.Video) {

        }
    }

    fun release() {
        mediaPlayer.release()
    }

    private fun resize() {
        val width = mediaPlayer.videoWidth
        val height = mediaPlayer.videoHeight

        if(height <= 800 || width <= height) {
            surfaceHolder.setFixedSize(width * 800 / height, 800)
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        Log.d("SIZE", "${mediaPlayer.videoWidth}, ${mediaPlayer.videoHeight}")
        resize()
        mediaPlayer.start()
        Toast.makeText(context, "mediaPlayer Starting ${mediaPlayer.isPlaying}", Toast.LENGTH_SHORT).show()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.d("DONE", "surfaceCreated")
        mediaPlayer.setDisplay(surfaceHolder)
    }
}