package io.untaek.animal.component

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class Viewer(val surfaceView: SurfaceView) : SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    private val context: Context = surfaceView.context
    private val tempFile: File  = File.createTempFile("1536092616210_gen", "webm")
    private val mediaPlayer: MediaPlayer = MediaPlayer().also {
        it.setOnPreparedListener(this)
    }
    private val surfaceHolder: SurfaceHolder = surfaceView.holder.also {
        it.addCallback(this)
    }

    fun load(path: String) {
        FirebaseStorage.getInstance().reference
                .child(path)
                .getFile(tempFile)
                .addOnSuccessListener {
                    Log.d("DONE", "firebase OnSuccess")
                    mediaPlayer.setDataSource(tempFile.path)
                    mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
                    mediaPlayer.prepareAsync()
                    Toast.makeText(context, it.totalByteCount.toString(), Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
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
        load("1536092616210_gen.webm")
    }
}