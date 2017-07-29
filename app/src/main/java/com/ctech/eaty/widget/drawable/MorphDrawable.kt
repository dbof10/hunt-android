package com.ctech.eaty.widget.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.util.Property
import com.ctech.eaty.util.AnimUtils


class MorphDrawable(@ColorInt color: Int, private var cornerRadius: Float) : Drawable() {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    companion object {

        val CORNER_RADIUS: Property<MorphDrawable, Float> = AnimUtils.createFloatProperty(object : AnimUtils.FloatProp<MorphDrawable>("cornerRadius") {
            override fun set(morphDrawable: MorphDrawable, value: Float) {
                morphDrawable.setCornerRadius(value)
            }

            override fun get(morphDrawable: MorphDrawable): Float {
                return morphDrawable.getCornerRadius()
            }
        })

        val COLOR: Property<MorphDrawable, Int> = AnimUtils.createIntProperty(object : AnimUtils.IntProp<MorphDrawable>("color") {
            override fun set(morphDrawable: MorphDrawable, color: Int) {
                morphDrawable.color = color
            }

            override fun get(morphDrawable: MorphDrawable): Int {
                return morphDrawable.color
            }
        })
    }

    init {
        paint.color = color
    }

    fun getCornerRadius(): Float {
        return cornerRadius
    }

    fun setCornerRadius(cornerRadius: Float) {
        this.cornerRadius = cornerRadius
        invalidateSelf()
    }

    var color: Int
        get() = paint.color
        set(@ColorInt color) {
            paint.color = color
            invalidateSelf()
        }

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(
                bounds.left.toFloat(),
                bounds.top.toFloat(),
                bounds.right.toFloat(),
                bounds.bottom.toFloat(),
                cornerRadius,
                cornerRadius,
                paint)
    }

    override fun getOutline(outline: Outline) {
        outline.setRoundRect(bounds, cornerRadius)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paint.colorFilter = cf
        invalidateSelf()
    }

    override fun getOpacity(): Int {
        return if (paint.alpha == 255) PixelFormat.OPAQUE else PixelFormat.TRANSLUCENT
    }


}