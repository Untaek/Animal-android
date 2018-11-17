package io.untaek.animal.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaCodec
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.Uri
import android.support.v4.content.FileProvider
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.untaek.animal.firebase.Fire
import java.io.File
import java.util.*

object UploadManager: RequestListener<File> {
    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<File>?, isFirstResource: Boolean): Boolean {
        Log.d("UploadManager", "failed", e)
        return false
    }

    override fun onResourceReady(resource: File?, model: Any?, target: Target<File>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
        Log.d("UploadManager", "${resource?.length()}")
        return false
    }

    fun getSize(context: Context, uri: Uri): Pair<Int, Int> {
        val mime = context.contentResolver.getType(uri)
        val type = mime.split("/")[0]

        var width = -1
        var height = -1

        if(type == "image") {
            val op = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri), null, op)

            width = op.outWidth
            height = op.outHeight
        }
        else if (type == "video") {
            val mr = MediaMetadataRetriever()
            mr.setDataSource(context, uri)
            width = mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH).toInt()
            height = mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT).toInt()
        }

        return Pair(width, height)
    }

    fun getMime(context: Context, uri: Uri) = context.contentResolver.getType(uri)!!

    fun createTempUri(context: Context, ext: String): Uri {
        val tempFile = File.createTempFile(Date().time.toString(), ext)
        return FileProvider.getUriForFile(context, Constants.FILE_PROVIDER, tempFile)
    }
}