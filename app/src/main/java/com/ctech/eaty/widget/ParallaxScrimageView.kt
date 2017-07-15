package com.ctech.eaty.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.util.AttributeSet
import android.util.Property
import com.ctech.eaty.R
import com.ctech.eaty.util.AnimUtils
import com.ctech.eaty.util.ColorUtils


/**
 * An image view which supports parallax scrolling and applying a scrim onto it's content. Get it.
 * <p>
 * It also has a custom pinned state, for use via state lists.
 */
class ParallaxScrimageView(context: Context, attrs: AttributeSet) : FourThreeImageView(context, attrs) {

    private val STATE_PINNED = intArrayOf(R.attr.state_pinned)
    private var scrimPaint: Paint
    private var imageOffset = 0
    private var minOffset = 0
    private val clipBoundsRect = Rect()
    private var scrimAlpha = 0f
    private var maxScrimAlpha = 1f
    private var scrimColor = Color.TRANSPARENT
    private var parallaxFactor = -0.5f
    private var isPinned = false
    private var immediatePin = false

    private val OFFSET: Property<ParallaxScrimageView, Int> by lazy {
        AnimUtils.createIntProperty(object : AnimUtils.IntProp<ParallaxScrimageView>("offset") {

            override fun set(parallaxScrimageView: ParallaxScrimageView, offset: Int) {
                parallaxScrimageView.setOffset(offset)
            }

            override fun get(parallaxScrimageView: ParallaxScrimageView): Int {
                return parallaxScrimageView.getOffset()
            }
        })
    }


    init {
        val a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ParallaxScrimageView)
        if (a.hasValue(R.styleable.ParallaxScrimageView_scrimColor)) {
            scrimColor = a.getColor(R.styleable.ParallaxScrimageView_scrimColor, scrimColor)
        }
        if (a.hasValue(R.styleable.ParallaxScrimageView_scrimAlpha)) {
            scrimAlpha = a.getFloat(R.styleable.ParallaxScrimageView_scrimAlpha, scrimAlpha)
        }
        if (a.hasValue(R.styleable.ParallaxScrimageView_maxScrimAlpha)) {
            maxScrimAlpha = a.getFloat(R.styleable.ParallaxScrimageView_maxScrimAlpha,
                    maxScrimAlpha)
        }
        if (a.hasValue(R.styleable.ParallaxScrimageView_parallaxFactor)) {
            parallaxFactor = a.getFloat(R.styleable.ParallaxScrimageView_parallaxFactor,
                    parallaxFactor)
        }
        a.recycle()

        scrimPaint = Paint()
        scrimPaint.color = ColorUtils.modifyAlpha(scrimColor, scrimAlpha.toInt())
    }

    fun getOffset(): Int {
        return translationY.toInt()
    }

    fun setOffset(offset: Int) {
        val newOffset = Math.max(minOffset, offset)
        if (newOffset.toFloat() != translationY) {
            translationY = newOffset.toFloat()
            imageOffset = (newOffset * parallaxFactor).toInt()
            clipBoundsRect.set(0, -newOffset, width, height)
            clipBounds = clipBoundsRect
            setScrimAlpha(Math.min((-newOffset / minimumHeight) * maxScrimAlpha, maxScrimAlpha))
            postInvalidateOnAnimation()
        }
        setPinned(newOffset == minOffset)
    }

    fun setScrimColor(@ColorInt scrimColor: Int) {
        if (this.scrimColor != scrimColor) {
            this.scrimColor = scrimColor
            postInvalidateOnAnimation()
        }
    }

    fun setScrimAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
        if (scrimAlpha != alpha) {
            scrimAlpha = alpha
            scrimPaint.color = ColorUtils.modifyAlpha(scrimColor, scrimAlpha)
            postInvalidateOnAnimation()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (h > minimumHeight) {
            minOffset = minimumHeight - h
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (imageOffset != 0) {
            val saveCount = canvas.save()
            canvas.translate(0f, imageOffset.toFloat())
            super.onDraw(canvas)
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), scrimPaint)
            canvas.restoreToCount(saveCount)
        } else {
            super.onDraw(canvas)
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), scrimPaint)
        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isPinned) {
            mergeDrawableStates(drawableState, STATE_PINNED)
        }
        return drawableState
    }

    fun isPinned(): Boolean {
        return isPinned
    }

    fun setPinned(isPinned: Boolean) {
        if (this.isPinned != isPinned) {
            this.isPinned = isPinned
            refreshDrawableState()
            if (isPinned && immediatePin) {
                jumpDrawablesToCurrentState()
            }
        }
    }

    fun isImmediatePin(): Boolean {
        return immediatePin
    }

    /**
     * As the pinned state is designed to work with a {@see StateListAnimator}, we may want to short
     * circuit this animation in certain situations e.g. when flinging a list.
     */
    fun setImmediatePin(immediatePin: Boolean) {
        this.immediatePin = immediatePin
    }
}