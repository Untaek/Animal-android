package io.untaek.animal.component

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import io.untaek.animal.firebase.Content
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Type
import java.io.File
import java.io.FileInputStream
import java.lang.Exception

class Viewer(val textureView: TextureView, val imageView: ImageView) : MediaPlayer.OnPreparedListener, TextureView.SurfaceTextureListener {

    private val context: Context = textureView.context

    private var bitmap: Bitmap? = null
    private var mediaPlayer: MediaPlayer? = null

    private var isSurfaceReady = false

    init {
        textureView.surfaceTextureListener = this

        textureView.setOnClickListener { _ ->
            mediaPlayer?.let {
                if(it.isPlaying) {
                    it.pause()
                }
                else {
                    it.start()
                }
            }
        }
    }

    private fun createMediaPlayer(){
        if(mediaPlayer == null) {
            Log.d("Viewer", "createMediaPlayer")
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setOnPreparedListener(this)
        }
    }

    private fun resize(content: Content) {
        val ratio = 0.7

        val p = Point()
        (context as Activity).windowManager.defaultDisplay.getSize(p)
        Log.d("Viewer", "Window size ${p.x} ${p.y}")

        val width = p.x
        val height = content.h * p.x / content.w

        if(height > p.y * 0.7) {
            textureView.layoutParams = FrameLayout.LayoutParams((width * ratio).toInt(), (height * ratio).toInt(), Gravity.CENTER)
            imageView.layoutParams = FrameLayout.LayoutParams((width * ratio).toInt(), (height * ratio).toInt(), Gravity.CENTER)
        }
        else {
            textureView.layoutParams = FrameLayout.LayoutParams(width, height, Gravity.CENTER)
            imageView.layoutParams = FrameLayout.LayoutParams(width, height, Gravity.CENTER)
        }
    }

    fun changeSource(content: Content){
        Log.d("Viewer", "changeSource")

        resize(content)

        Fire.getInstance().loadActualContent(content, context, object: Fire.Callback {
            override fun onResult(data: Any) {
                Log.d("Post", data.toString())
                if(content.type == Type.Image){
                    bitmap = data as Bitmap
                    imageView.setImageBitmap(bitmap)
                    Log.d("Bitmap Size", "${bitmap?.width} ${bitmap?.height}")
                }
                else {
                    val uri = FileProvider.getUriForFile(context, "io.untaek.animal.fileprovider", data as File)
                    createMediaPlayer()
                    mediaPlayer!!.setDataSource(context, uri)
                    mediaPlayer!!.prepareAsync()
                }
            }

            override fun onFail(e: Exception) {
                Log.w("Post", "error", e)
            }
        })
    }

    fun release() {
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onPrepared(mp: MediaPlayer?) {
        Log.d("SIZE", "${mediaPlayer?.videoWidth}, ${mediaPlayer?.videoHeight}")
        mediaPlayer?.seekTo(1)
        //mediaPlayer?.start()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d("SurfaceTexture", "SizeChanged")
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        Log.d("SurfaceTexture", "Destroyed")
        isSurfaceReady = false
        release()

        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d("SurfaceTexture", "Available")
        isSurfaceReady = true
        createMediaPlayer()
        mediaPlayer?.setSurface(Surface(surface))
    }
}