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
import io.untaek.animal.firebase.Content
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Type
import java.io.File
import java.lang.Exception
import java.lang.IllegalStateException

class Viewer(val textureView: TextureView, val imageView: ImageView) :
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        TextureView.SurfaceTextureListener {

    private val context: Context = textureView.context

    private var bitmap: Bitmap? = null
    private var mediaPlayer: MediaPlayer? = null

    private var content: Content? = null

    private var isSurfaceReady = false

    init {
        createMediaPlayer()

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
            mediaPlayer!!.setOnErrorListener(this)
        }
    }

    private fun resize(content: Content) {
        val ratio = 0.6

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
        this.content = content
        fetch()
    }

    private fun fetch() {
        content?.let {
            resize(it)

            Fire.getInstance().loadActualContent(it, context, object: Fire.Callback<Any> {
                override fun onResult(data: Any) {
                    Log.d("Post", data.toString())
                    if(it.mime.split("/")[0] == "image"){
                        imageView.visibility = View.VISIBLE
                        textureView.visibility = View.GONE
                        bitmap = data as Bitmap
                        imageView.setImageBitmap(bitmap)
                        Log.d("Bitmap Size", "${bitmap?.width} ${bitmap?.height}")
                    }
                    else {
                        imageView.visibility = View.GONE
                        textureView.visibility = View.VISIBLE
                        val uri = FileProvider.getUriForFile(context, "io.untaek.animal.fileprovider", data as File)

                        Thread{
                            try {
                                while (!isSurfaceReady) {}
                                mediaPlayer!!.reset()
                                mediaPlayer!!.setDataSource(context, uri)
                                mediaPlayer!!.prepare()
                                mediaPlayer!!.seekTo(1)
                            } catch (e: IllegalStateException) {
                                Log.e("fetch", "IllegalStateException", e)
                                mediaPlayer!!.reset()
                            }
                        }.start()
                    }
                }

                override fun onFail(e: Exception) {
                    Log.w("Post", "error", e)
                }
            })
        }

    }

    private fun release() {
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onPrepared(mp: MediaPlayer?) {
        Log.d("SIZE", "${mediaPlayer?.videoWidth}, ${mediaPlayer?.videoHeight}")
        //mediaPlayer?.seekTo(1)
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.w("MediaPlayer", "error $what $extra")
        return false
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d("SurfaceTexture", "SizeChanged")
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        Log.d("SurfaceTexture", "Destroyed")
        isSurfaceReady = false
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d("SurfaceTexture", "Available")
        mediaPlayer!!.setSurface(Surface(textureView.surfaceTexture))
        isSurfaceReady = true
    }
}