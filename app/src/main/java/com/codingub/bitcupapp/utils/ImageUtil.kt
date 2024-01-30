package com.codingub.bitcupapp.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.codingub.bitcupapp.App
import com.codingub.bitcupapp.R
import com.codingub.bitcupapp.presentation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.OutputStream

object ImageUtil {

    private val context: Context get() = App.getInstance()

    fun load(@DrawableRes drawableRes: Int, imageView: ImageView) {
        Glide.with(context).load(drawableRes).thumbnail(
            Glide.with(context)
                .asDrawable().sizeMultiplier(0.6f)
        ).into(imageView)
    }

    fun load(drawable: Drawable, imageView: ImageView) {
        Glide.with(context).load(drawable)
            .thumbnail(
                Glide.with(context)
                    .asDrawable().sizeMultiplier(0.6f)
            ).into(imageView)
    }

    fun load(uri: Uri, imageView: ImageView) {
        Glide.with(context)
            .load(uri)
            .thumbnail(
                Glide.with(context)
                    .asDrawable().sizeMultiplier(0.6f)
            )
            .into(imageView)
    }

    fun load(@DrawableRes drawableRes: Int, onLoaded: (Drawable) -> Unit) {
        Glide.with(context).load(drawableRes).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                resource.let {
                    CoroutineScope(Dispatchers.Main).launch {
                        onLoaded(it)
                    }
                }
                return false
            }
        }).submit()
    }

    fun load(drawable: Drawable, onLoaded: (Drawable) -> Unit) {
        Glide.with(context).load(drawable).listener(object : RequestListener<Drawable> {

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                resource.let {
                    CoroutineScope(Dispatchers.Main).launch {
                        onLoaded(it)
                    }
                }
                return false
            }
        }).submit()
    }

    fun load(uri: Uri, onLoaded: (Drawable) -> Unit) {
        Glide.with(context)
            .load(uri)
            .thumbnail(
                Glide.with(context)
                    .asDrawable().sizeMultiplier(0.6f)
            )
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    resource.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            onLoaded(it)
                        }
                    }
                    return false
                }
            })
            .submit()
    }

    fun saveBitmapAsImageToDevice(context: Context, bitmap: Bitmap?) {
        // Add a specific media item.
        val resolver = MainActivity.getInstance().contentResolver

        val imageStorageAddress = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "my_app_${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis())
        }

        try {
            // Save the image.
            val contentUri: Uri? = resolver.insert(imageStorageAddress, imageDetails)
            contentUri?.let { uri ->
                // Don't leave an orphan entry in the MediaStore
                if (bitmap == null) resolver.delete(contentUri, null, null)
                val outputStream: OutputStream? = resolver.openOutputStream(uri)
                outputStream?.let { outStream ->
                    val isBitmapCompressed =
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 95, outStream)
                    if (isBitmapCompressed == true) {
                        outStream.flush()
                        outStream.close()
                    }
                } ?: throw IOException("Failed to get output stream.")
            } ?: throw IOException("Failed to create new MediaStore record.")

            Toast.makeText(context, Resource.string(R.string.image_downloaded), Toast.LENGTH_SHORT)
                .show()
        } catch (e: IOException) {
            throw e
        }
    }

    fun loadBitmapFromUri(uri: Uri, context: Context, callback: (Bitmap?) -> Unit) {
        Glide.with(context)
            .asBitmap()
            .load(uri)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    callback(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) = Unit
            })
    }

}