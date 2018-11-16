//package io.untaekPost.animal.tab
//
//import android.Manifest
//import android.app.Activity.RESULT_OK
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.support.v4.app.ActivityCompat
//import android.support.v4.app.Fragment
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.TextureView
//import android.view.View
//import android.view.ViewGroup
//
//const val PERMISSION_ALL = 99
//const val PERMISSION_EXTERNAL_STORAGE = 100
//const val REQUEST_MEDIA_URI = 200
//
//class TabUploadFragment_lagacy: Fragment(), Camera.Callback {
//
//    private lateinit var textureView: TextureView
//    private lateinit var camera: Camera
//    private lateinit var filePath: String
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        Log.d("onRequestPermissions ", permissions.contentToString())
//        when(requestCode) {
//            PERMISSION_ALL -> {
//                if(grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
//                    textureView.surfaceTextureListener = camera.surfaceTextureListener
//                }
//                else {
//                    Log.d("onRequestPermissions ", "Permission denied!!")
//                }
//            }
//            PERMISSION_EXTERNAL_STORAGE -> {
//                if(grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
//                    openEditActivity()
//                }
//                else {
//                    Log.d("onRequestPermissions ", "Permission denied!!")
//                }
//            }
//        }
//        return
//    }
//
//    override fun onCameraReady(cameraSurface: Camera) {
//        Log.d("camera", "ready!")
//        cameraSurface.openCamera()
//    }
//    override fun onRecordingStart() {}
//    override fun onRecordingStop(path: String) {
//        filePath = path
//
//        Log.d("onRecordingStop ", "recording finished. $path")
//        openEditActivity()
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val permissionDenied = arrayOf(
//                Manifest.permission.CAMERA,
//                Manifest.permission.RECORD_AUDIO
//        ).filter { ActivityCompat.checkSelfPermission(activity!!, it) == PackageManager.PERMISSION_DENIED }
//                .toTypedArray()
//
//        if(permissionDenied.isNotEmpty()){
//            requestPermissions(permissionDenied, PERMISSION_ALL)
//        }
//
//        camera = Camera(activity!!, this)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val root = inflater.inflate(R.layout.tab_upload, container, false)
//
//        textureView = root.textureView
//
//        if(::camera.isInitialized){
//            textureView.surfaceTextureListener = camera.surfaceTextureListener
//        }
//
//        root.btn_gallery.setOnClickListener {
//
//            val intent = Intent(Intent.ACTION_PICK).apply {
//                type = "*/*"
//                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("video/*", "images/*"))
//            }
//
//            startActivityForResult(intent, REQUEST_MEDIA_URI)
//        }
//
//        root.btn_record.setOnClickListener {
//            camera.updateRecordState()
//        }
//
//        return root
//    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_MEDIA_URI) {
//            if (resultCode == RESULT_OK) {
//                val uri: Uri = data?.data!!
//                Log.d("onActivityResult: ", "URI: " + uri.toString())
//                val cursor = context?.contentResolver?.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)?.apply {
//                    moveToFirst()
//                }
//                Log.d("onActivityResult: ", "cursor: " + cursor?.columnNames?.contentDeepToString())
//                filePath = cursor?.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))!!
//
//                cursor.close()
//
//                Log.d("onActivityResult: ", "File path: " + filePath)
//                val permissionDenied = arrayOf(
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                ).filter { ActivityCompat.checkSelfPermission(activity!!, it) == PackageManager.PERMISSION_DENIED }
//                        .toTypedArray()
//
//                if(permissionDenied.isNotEmpty()){
//                    requestPermissions(permissionDenied, PERMISSION_EXTERNAL_STORAGE)
//                }
//                else {
//                    openEditActivity()
//                }
//            }
//        }
//    }
//
//    private fun openEditActivity() {
//        val intent = Intent(activity, EditMediaActivity::class.java).apply {
//            putExtra("path", filePath)
//        }
//        startActivity(intent)
//    }
//
//    companion object {
//        fun instance(): TabUploadFragment {
//            return TabUploadFragment()
//        }
//    }
//}
