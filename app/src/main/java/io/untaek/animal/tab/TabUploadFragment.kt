package io.untaek.animal.tab

import android.Manifest
import android.app.Fragment
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.Size
import android.view.*
import io.untaek.animal.R
import kotlinx.android.synthetic.main.tab_upload.*
import kotlinx.android.synthetic.main.tab_upload.view.*
import java.io.File
import java.util.*

const val PERMISSION_REQUEST_CAMERA = 100

class TabUploadFragment: Fragment(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var cameraDevice: CameraDevice
    private lateinit var surfaceView: SurfaceView
    private lateinit var textureView: TextureView
    private lateinit var previewRequestBuilder: CaptureRequest.Builder
    private lateinit var thread: HandlerThread
    private lateinit var threadHandler: Handler
    private lateinit var surface: Surface

    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(texture: SurfaceTexture, width: Int, height: Int) {
        }

        override fun onSurfaceTextureSizeChanged(texture: SurfaceTexture, width: Int, height: Int) {
        }

        override fun onSurfaceTextureDestroyed(texture: SurfaceTexture) = true

        override fun onSurfaceTextureUpdated(texture: SurfaceTexture) = Unit

    }

    private val cameraStateCallback = object: CameraDevice.StateCallback(){
        override fun onOpened(camera: CameraDevice?) {
            Log.d("camera2State: ", "onOpened")
            cameraDevice = camera!!

            textureView.surfaceTexture.setDefaultBufferSize(500, 500)
            surface = Surface(textureView.surfaceTexture)

            cameraDevice.createCaptureSession(Collections.singletonList(surface), cameraCaptureSessionCallback, threadHandler)

            previewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            previewRequestBuilder.addTarget(surface)
        }

        override fun onDisconnected(camera: CameraDevice?) {
            Log.d("camera2State: ", "onDisconnected")
        }

        override fun onError(camera: CameraDevice?, error: Int) {
            Log.d("camera2State: ", "onError")
        }
    }

    private val cameraCaptureSessionCallback = object: CameraCaptureSession.StateCallback() {
        override fun onConfigureFailed(session: CameraCaptureSession?) {

        }

        override fun onConfigured(session: CameraCaptureSession?) {
            Log.d("camera2State: ", "onConfigured")
            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_VIDEO)
            val previewRequest = previewRequestBuilder.build()
            session?.setRepeatingRequest(previewRequest, cameraCaptureCallback, threadHandler)
        }
    }

    private val cameraCaptureCallback = object: CameraCaptureSession.CaptureCallback() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater!!.inflate(R.layout.tab_upload, container, false)

        val openCamera = root.open_camera
        val openGallery = root.open_gallery

        openCamera.setOnClickListener { v ->
            v.visibility = View.INVISIBLE
            openGallery.visibility = View.INVISIBLE
        }

        thread = HandlerThread("CameraBackground").also { it.start() }
        threadHandler = Handler(thread.looper)

        this.textureView = root.textureView
        this.textureView.surfaceTextureListener = object: TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
                Log.d("surfaceTextureState: ", "onSurfaceTextureDestroyed")
                return true
            }

            override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
                val cameraManager: CameraManager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                val cameraList = cameraManager.cameraIdList

                var cameraId = ""
                for(id in cameraList) {
                    val cameraChar = cameraManager.getCameraCharacteristics(id)
                    if(cameraChar.get(CameraCharacteristics.LENS_FACING)
                            == CameraCharacteristics.LENS_FACING_BACK)
                        cameraId = id
                }

                if(ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
                }

                try {
                    cameraManager.openCamera(cameraId, cameraStateCallback, threadHandler)
                } catch (e: SecurityException) {
                    Log.d("PERMISSION!", e.toString())
                }            }

        }



        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        thread.quit()
    }

    companion object {
        fun instance(): TabUploadFragment {
            return TabUploadFragment()
        }
    }
}
