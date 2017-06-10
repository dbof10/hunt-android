package com.ctech.eaty.util

import android.widget.ImageView

interface ImageLoader {

    fun cancel(imageView: ImageView)

    fun downloadInto(url: String, imageView: ImageView)

}