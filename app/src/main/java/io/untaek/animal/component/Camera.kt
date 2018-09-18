package io.untaek.animal.component

import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.MediaRecorder
import android.util.Size
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.TextureView
import android.opengl.ETC1.getWidth
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList


class Camera(val context: Context, val callback: Callback): TextureView.SurfaceTextureListener {

    interface Callback {
        fun onCameraReady(cameraSurface: Camera)
        fun onRecordingStart()
        fun onRecordingStop(path: String)
    }

    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String
    private lateinit var cameraChar: CameraCharacteristics
    private lateinit var cameraSize: Size
    private lateinit var cameraDevice: CameraDevice
    private lateinit var surface: Surface
    private lateinit var backgroundThread: HandlerThread
    private lateinit var backgroundThreadHandler: Handler
    private lateinit var mediaRecorder: MediaRecorder
    private var previewSession: CameraCaptureSession? = null

    private var isRecording = false
    private lateinit var path: String

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {}
    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {}
    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        backgroundThread.quitSafely()
        return true
    }
    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d("CameraSurface", "onSurfaceTextureAvailable")
        this.surface = Surface(surface)
        cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraIds = cameraManager.cameraIdList
        for (id in cameraIds) {
            val char = cameraManager.getCameraCharacteristics(id)
            if(char.get(CameraCharacteristics.LENS_FACING)
                    == CameraCharacteristics.LENS_FACING_BACK) {
                cameraId = id
                cameraChar = char
                break
            }
        }

        val map = cameraChar.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP) ?:
                throw RuntimeException("Cannot get available preview/video sizes")

        val sizes = map.getOutputSizes(SurfaceTexture::class.java)
        cameraSize = sizes[0]

        for (size in sizes) {
            if (size.width > cameraSize.width) {
                cameraSize = size
            }
        }

        backgroundThread = HandlerThread("CameraBackgroundThread").also { it.start() }
        backgroundThreadHandler = Handler(backgroundThread.looper)
        callback.onCameraReady(this)
    }

    private val cameraStateCallback = object: CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            startPreview()
        }

        override fun onDisconnected(camera: CameraDevice?) {
            backgroundThread.quitSafely()        }

        override fun onError(camera: CameraDevice?, error: Int) {
            backgroundThread.quitSafely()        }
    }

    private fun startPreview() {
        previewSession?.close()
        previewSession = null

        cameraDevice.createCaptureSession(Arrays.asList(surface), object: CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession) {
                previewSession = session
                val request = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW).apply {
                    addTarget(surface)
                }
                session.setRepeatingRequest(request?.build(), null, backgroundThreadHandler)
            }

            override fun onConfigureFailed(session: CameraCaptureSession?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }, backgroundThreadHandler)
    }

    private fun startRecord() {
        previewSession?.close()
        previewSession = null

        mediaRecorder = MediaRecorder().apply {
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.WEBM)
            setOutputFile(path)
            setVideoEncodingBitRate(10000000)
            setVideoFrameRate(30)
            setVideoSize(1280, 720)
            setVideoEncoder(MediaRecorder.VideoEncoder.VP8)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            prepare()
        }

        val recorderSurface = mediaRecorder.surface
        val surfaces = ArrayList<Surface>().apply {
            add(surface)
            add(recorderSurface)
        }

        val builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD).apply {
            addTarget(surface)
            addTarget(recorderSurface)
            set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        }

        cameraDevice.createCaptureSession(surfaces, object: CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession?) {
                previewSession = session
                previewSession?.setRepeatingRequest(builder.build(), null, backgroundThreadHandler)
                mediaRecorder.start()
            }
            override fun onConfigureFailed(session: CameraCaptureSession?) {
                Log.d("onConfigureFailed", "")
            }
        }, backgroundThreadHandler)
    }

    private fun stopRecord() {
        mediaRecorder.stop()
        mediaRecorder.reset()

        startPreview()
    }

    val surfaceTextureListener = this

    fun openCamera() {
        try {
            cameraManager.openCamera(cameraId, cameraStateCallback, backgroundThreadHandler)
        } catch (e: SecurityException) {
            Log.d("openCamera: ", e.toString())
        }
    }

    fun updateRecordState() {
        if (!isRecording) {
            path = "${context.filesDir.absolutePath}/${System.currentTimeMillis()}.mp4"
            startRecord()
            callback.onRecordingStart()
        }
        else {
            stopRecord()
            callback.onRecordingStop(path)
        }
        isRecording = !isRecording

        Log.d("updateRecordState ", isRecording.toString())
    }
}