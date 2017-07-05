package com.ctech.eaty.util

import android.content.Context
import android.graphics.Bitmap
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.ctech.eaty.R


class GlideImageLoader(context: Context) {

    private val glide: Glide = Glide.get(context)

    companion object {
        fun getBitmap(glideDrawable: GlideDrawable): Bitmap? {
            if (glideDrawable is GlideBitmapDrawable) {
                return glideDrawable.bitmap
            } else if (glideDrawable is GifDrawable) {
                return glideDrawable.firstFrame
            }
            return null
        }
    }

    fun downloadInto(url: String, imageView: ImageView) {
        Glide.with(imageView.context)
                .load(url)
                .into(imageView)
    }

    fun cancel(imageView: ImageView) {
        Glide.clear(imageView)
    }

    fun downloadInto(url: String, imageView: ImageView,
                     listener: RequestListener<String, GlideDrawable>, @DrawableRes placeholderRes: Int = R.drawable.ic_photo_placeholder) {
        Glide.with(imageView.context)
                .load(url)
                .listener(listener)
                .placeholder(placeholderRes)
                .into(imageView)
    }

    fun clearMemory() {
        glide.clearMemory()
    }
}