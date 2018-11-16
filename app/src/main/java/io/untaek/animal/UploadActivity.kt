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
import java.io.File
import java.io.OutputStream
import java.lang.Exception
import java.util.*

class UploadActivity : AppCompatActivity() {

    private var target = SelectPictureComponent.GALLERY
    private var requestCode = REQUEST_GET_CONTENT_FROM_GALLERY
    private val stepWriteDetailFragment: Fragment = StepWriteDetail.instance()

    private lateinit var mIntent: Intent
    private lateinit var contentUri: Uri
    private lateinit var contentFile: File

    companion object {
        const val REQUEST_GET_CONTENT_FROM_GALLERY = 23
        const val REQUEST_TAKE_PHOTO = 44
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_TAKE_PHOTO)
            PermissionHelper.handleRequestResult(permissions, grantResults, {
                startActivityForResult(mIntent, requestCode)
            }, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK) {
            stepWriteDetailFragment.arguments = Bundle()

            when(requestCode) {
                REQUEST_GET_CONTENT_FROM_GALLERY -> {
                    contentUri = data!!.data
                }
                REQUEST_TAKE_PHOTO -> {

                }
            }
            stepWriteDetailFragment.arguments?.putString("uri", contentUri.toString())
            replaceFragment(stepWriteDetailFragment)
        }
        else{
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        target = intent.getIntExtra(SelectPictureComponent.TARGET, 0)

        button_goback.setOnClickListener { finish() }
        button_submit.setOnClickListener { finish() }

        mIntent = when(target) {
            SelectPictureComponent.GALLERY -> Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/* video/*"
                requestCode = REQUEST_GET_CONTENT_FROM_GALLERY
            }
            SelectPictureComponent.CAMERA -> Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                contentUri = UploadManager.createTempUri(this, ".jpg")
                it.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
                requestCode = REQUEST_TAKE_PHOTO
            }
            else -> throw Exception()
        }

        if(requestCode == REQUEST_GET_CONTENT_FROM_GALLERY) {
            startActivityForResult(mIntent, requestCode)
        }else {
            if(PermissionHelper.checkAndRequestPermission(this, Manifest.permission.CAMERA, REQUEST_TAKE_PHOTO)) {
                startActivityForResult(mIntent, requestCode)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commitAllowingStateLoss()
    }

    class StepSelectImage: Fragment() {
        companion object {
            fun instance() = StepSelectImage()
        }
    }

    class StepWriteDetail: Fragment(), RequestListener<Drawable> {
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

            requireContext().grantUriPermission("io.untaek.animal", contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

            Fire.getInstance().newPost(requireContext(), mapOf(), root.editText_description.text.toString(), contentUri,
                    object : Fire.Callback{
                        override fun onResult(data: Any) {
                            Log.d("StepWriteDetailFragment", "done!")
                        }

                        override fun onFail(e: Exception) {
                            Log.d("StepWriteDetailFragment", "Fail...!")
                        }

                    }, null)
        }

        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            isImageLoading = false
            progressBar.visibility = View.GONE


            return false
        }
    }
}
