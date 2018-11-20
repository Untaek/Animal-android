package io.untaek.animal

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.untaek.animal.component.SelectPictureComponent
import io.untaek.animal.firebase.Content
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.Type
import io.untaek.animal.util.Constants
import io.untaek.animal.util.PermissionHelper
import io.untaek.animal.util.UploadManager
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.activity_upload.view.*
import kotlinx.android.synthetic.main.fragment_upload_write_detail.*
import kotlinx.android.synthetic.main.fragment_upload_write_detail.view.*
import kotlinx.android.synthetic.main.tab_upload.view.*
import java.io.File
import java.io.OutputStream
import java.lang.Exception
import java.util.*

class UploadActivity : AppCompatActivity() {

    private var target = SelectPictureComponent.IMAGE
    private val stepWriteDetailFragment: Fragment = StepWriteDetail.instance()

    private lateinit var mIntent: Intent
    private lateinit var contentUri: Uri

    companion object {
        const val REQUEST_GET_IMAGE = 23
        const val REQUEST_GET_VIDEO = 55
        const val REQUEST_TAKE_PHOTO = 44
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_TAKE_PHOTO ->
                PermissionHelper.handleRequestResult(permissions, grantResults, {
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                        it.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
                        startActivityForResult(it, requestCode)
                    }
                }, null)
            REQUEST_GET_IMAGE, REQUEST_GET_VIDEO -> {
                    PermissionHelper.handleRequestResult(permissions, grantResults, {
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also {
                            it.type = if (requestCode == REQUEST_GET_IMAGE) "image/*" else "video/*"
                            startActivityForResult(it, requestCode)
                        }
                    }, null)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_GET_IMAGE || requestCode == REQUEST_GET_VIDEO) {
                contentUri = data!!.data
            }

            stepWriteDetailFragment.arguments = Bundle().apply {
                putString("uri", contentUri.toString())
            }

            replaceFragment(stepWriteDetailFragment)
        }
        else{
            finish()
        }
    }

    private fun openImageGallery() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also {
            it.type = "image/*"
            if(PermissionHelper.checkAndRequestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_GET_IMAGE)){
                startActivityForResult(it, REQUEST_GET_IMAGE)
            }
        }
    }

    private fun openVideoGallery() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also {
            it.type = "video/*"
            if(PermissionHelper.checkAndRequestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_GET_VIDEO)){
                startActivityForResult(it, REQUEST_GET_VIDEO)
            }
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            contentUri = UploadManager.createTempUri(this, ".jpg")
            it.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
            if(PermissionHelper.checkAndRequestPermission(this, Manifest.permission.CAMERA, REQUEST_TAKE_PHOTO)) {
                startActivityForResult(it, REQUEST_TAKE_PHOTO)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commitAllowingStateLoss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        button_goback.setOnClickListener { finish() }

        target = intent.getIntExtra(SelectPictureComponent.TARGET, 0)

        /**
         * 카메라와 비디오 갤러리가 반대로 열림 왜그런지는 모름
         */
        when(target) {
            SelectPictureComponent.IMAGE -> openImageGallery()
            SelectPictureComponent.CAMERA -> openVideoGallery()
            SelectPictureComponent.VIDEO -> openCamera()
            else -> throw Exception()
        }
    }

    class StepSelectImage: Fragment() {
        companion object {
            fun instance() = StepSelectImage()
        }
    }

    class StepWriteDetail: Fragment(), RequestListener<Drawable>, Fire.Callback<Any> {
        private lateinit var contentUri: Uri
        private var isImageLoading = true

        companion object {
            fun instance() = StepWriteDetail()
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_upload_write_detail, container, false)
        }

        override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
            Log.d("StepWriteDetailFragment", arguments?.getString("uri"))

            contentUri = Uri.parse(arguments?.getString("uri"))

            Glide.with(requireContext())
                    .load(contentUri)
                    .addListener(this)
                    .into(root.imageView)

            root.button_submit.setOnClickListener {
                Fire.getInstance().newPost(requireContext(), mapOf(), root.editText_description.text.toString(), contentUri,
                        this, null)
                Toast.makeText(requireContext(), "업로드 중입니다..", Toast.LENGTH_SHORT).show()
                activity?.finish()
            }
        }

        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            isImageLoading = false
            progressBar.visibility = View.GONE
            return false
        }

        override fun onResult(data: Any) {
            Log.d("StepWriteDetailFragment", "done!")
        }

        override fun onFail(e: Exception) {
            Log.d("StepWriteDetailFragment", "Fail...!")
        }
    }
}
