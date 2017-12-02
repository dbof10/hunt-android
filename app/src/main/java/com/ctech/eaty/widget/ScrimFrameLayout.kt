package com.ctech.eaty.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import com.ctech.eaty.R

class ScrimFrameLayout : FrameLayout {

    private val STATE_PINNED = intArrayOf(R.attr.state_pinned)
    private var minOffset = 0
    private val clipBoundsRect = Rect()
    private var isPinned = false
    private var immediatePin = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun getOffset(): Int {
        return translationY.toInt()
    }

    fun setOffset(offset: Int) {
        val newOffset = Math.max(minOffset, offset)
        if (newOffset.toFloat() != translationY) {
            translationY = newOffset.toFloat()
            clipBoundsRect.set(0, -newOffset, width, height)
            clipBounds = clipBoundsRect
            postInvalidateOnAnimation()
        }
        setPinned(newOffset == minOffset)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (h > minimumHeight) {
            minOffset = minimumHeight - h
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

    fun setImmediatePin(immediatePin: Boolean) {
        this.immediatePin = immediatePin
    }
}