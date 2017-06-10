package com.ctech.eaty.util

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader : ImageLoader {

    override fun downloadInto(url: String, imageView: ImageView) {
        Glide.with(imageView.context)
                .load(url)
                .into(imageView)
    }

    override fun cancel(imageView: ImageView) {
        Glide.clear(imageView)
    }
}