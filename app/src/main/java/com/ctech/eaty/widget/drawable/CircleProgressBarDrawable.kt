package com.ctech.eaty.widget.drawable;

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import com.ctech.eaty.R
import com.facebook.drawee.drawable.ProgressBarDrawable

class CircleProgressBarDrawable constructor(context: Context) : ProgressBarDrawable() {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var currentLevel = 0
    private val maxLevel = 10000
    private var strokeWidth = 0
    private var progressColor = 0
    private var radiusSize = 0

    init {
        this.strokeWidth = context.resources.getDimensionPixelSize(R.dimen.progress_stroke_width)
        this.progressColor = ContextCompat.getColor(context, R.color.colorAccent)
        this.radiusSize = context.resources.getDimensionPixelSize(R.dimen.progressbar_size) / 2
    }

    override fun onLevelChange(level: Int): Boolean {
        this.currentLevel = level
        invalidateSelf()
        return true
    }

    override fun draw(canvas: Canvas) {
        if (hideWhenZero && currentLevel == 0) {
            return
        }
        drawBar(canvas, maxLevel, backgroundColor)
        drawBar(canvas, currentLevel, progressColor)
    }

    private fun drawBar(canvas: Canvas, level: Int, color: Int) {
        val bounds = bounds

        val halfHorizon = bounds.right * .5F
        val halfVertical = bounds.bottom * .5F

        val left = halfHorizon - radiusSize
        val right = halfHorizon + radiusSize
        val top = halfVertical
        val bottom = halfVertical + radiusSize * 2
        val rectF = RectF(left, top, right, bottom)

        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth.toFloat()
        if (level != 0) {
            canvas.drawArc(rectF, -90F, (level * 360F / maxLevel), false, paint)
        }
    }


}