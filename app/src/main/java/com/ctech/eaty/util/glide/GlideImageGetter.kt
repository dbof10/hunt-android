package com.ctech.eaty.util.glide

import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Html.ImageGetter
import android.util.Size
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.ctech.eaty.entity.DisplayNodeType


class GlideImageGetter(private val targetView: TextView) : ImageGetter, Drawable.Callback {
    private val imageTargets = ArrayList<Target<Drawable>>()

    private var deferHolderWidth = View.MeasureSpec.UNSPECIFIED
    private var deferHolderHeight = View.MeasureSpec.UNSPECIFIED

    init {
        targetView.tag = this
    }

    override fun getDrawable(url: String): Drawable {

        val uri = Uri.parse(url)
        val width = uri.getQueryParameter("w").toInt()
        val height = uri.getQueryParameter("h").toInt()
        val type = DisplayNodeType.valueOf(uri.getQueryParameter("type").toUpperCase())

        val content = Size(width, height)
        val container = Size(deferHolderWidth, deferHolderHeight)
        val imageTarget = DrawableTarget(targetView.context, content, container, type)
        val asyncWrapper = imageTarget.lazyDrawable
        asyncWrapper.callback = this

        Glide.with(targetView)
                .load(url)
                .into(imageTarget)
        imageTargets.add(imageTarget)
        return asyncWrapper
    }

    fun clear() {
        for (target in imageTargets) {
            Glide
                    .with(targetView)
                    .clear(target)
        }
    }

    fun clear(view: TextView) {
        view.text = null
        val tag = view.tag
        if (tag is GlideImageGetter) {
            tag.clear()
            view.tag = null
        }
    }

    override fun invalidateDrawable(who: Drawable) {
        targetView.invalidate()
    }

    override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {

    }

    override fun unscheduleDrawable(who: Drawable, what: Runnable) {

    }

    fun setHolderWidth(width: Int) {
        this.deferHolderWidth = width
    }

    fun setHolderHeight(height: Int) {
        this.deferHolderHeight = height
    }

}