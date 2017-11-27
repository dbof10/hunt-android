package com.ctech.eaty.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.ctech.eaty.R


class GlideImageLoader(private val context: Context) {

    private val glide: Glide = Glide.get(context)

    companion object {
        fun getBitmap(drawable: Drawable): Bitmap? {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            } else if (drawable is GifDrawable) {
                return drawable.firstFrame
            }
            return null
        }
    }

    fun downloadInto(url: String?, imageView: ImageView) {
        val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
    }

    fun downloadInto(@DrawableRes drawableRes: Int, imageView: ImageView) {
        Glide.with(imageView.context)
                .load(drawableRes)
                .into(imageView)
    }

    fun downloadInto(url: String, imageView: ImageView,
                     listener: RequestListener<Drawable>, @DrawableRes placeholderRes: Int = R.drawable.ic_photo_placeholder) {
        val options = RequestOptions()
                .placeholder(placeholderRes)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

        Glide.with(context)
                .load(url)
                .listener(listener)
                .apply(options)
                .into(imageView)
    }

    fun clearMemory() {
        glide.clearMemory()
    }

}