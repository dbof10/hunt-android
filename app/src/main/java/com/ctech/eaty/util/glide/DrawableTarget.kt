package com.ctech.eaty.util.glide

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.drawable.DrawableWrapper
import android.util.Size
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.ctech.eaty.R
import com.ctech.eaty.entity.DisplayNodeType


class DrawableTarget(private val context: Context,
                     content: Size,
                     container: Size,
                     private val type: DisplayNodeType) : SimpleTarget<Drawable>(content.width, content.height) {


    private val transparentDrawable = ColorDrawable(Color.TRANSPARENT)
    private val wrapper = DrawableWrapper(null)

    val lazyDrawable
        get() = wrapper

    init {
        setDrawable(null)
        wrapper.setBounds(0, 0, container.width, (container.width * content.height) / content.width)
    }

    override fun onLoadStarted(placeholder: Drawable?) {
        setDrawable(placeholder)
    }


    override fun onLoadFailed(errorDrawable: Drawable?) {
        setDrawable(errorDrawable)
    }


    override fun onResourceReady(drawable: Drawable, transition: Transition<in Drawable>?) {
        if (drawable is GifDrawable) {
            drawable.setLoopCount(GifDrawable.LOOP_FOREVER)
            drawable.start()
        }
        if (type == DisplayNodeType.IMAGE) {
            setDrawable(drawable)
        } else if (type == DisplayNodeType.VIDEO) {
            val playDrawable = ContextCompat.getDrawable(context, R.drawable.ic_youtube)
            val layers = arrayOf(drawable, playDrawable)
            val layerDrawable = LayerDrawable(layers)
            setDrawable(layerDrawable)
        }
    }


    override fun onLoadCleared(placeholder: Drawable?) {
        setDrawable(placeholder)
    }

    private fun setDrawable(drawee: Drawable?) {
        var drawable = drawee
        if (drawable == null) {
            drawable = transparentDrawable
        }

        drawable.bounds = wrapper.bounds
        wrapper.setWrappedDrawable(drawable)
        wrapper.invalidateSelf()
    }

}