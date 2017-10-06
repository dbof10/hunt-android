package com.ctech.eaty.widget

import android.content.Context
import android.support.v4.view.ViewCompat.setTranslationY
import android.support.v4.view.ViewCompat.getTranslationY
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import android.widget.ImageButton


class FABToggle(context: Context, attrs: AttributeSet) : ImageButton(context, attrs), Checkable {

    private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    private var isChecked = false
    private var minOffset: Int = 0

    fun setOffset(offset: Int) {
        var newOffset: Int
        if (offset.toFloat() != translationY) {
            newOffset = Math.max(minOffset, offset)
            translationY = newOffset.toFloat()
        }
    }

    fun setMinOffset(minOffset: Int) {
        this.minOffset = minOffset
    }

    override fun isChecked(): Boolean {
        return isChecked
    }

    override fun setChecked(isChecked: Boolean) {
        if (this.isChecked != isChecked) {
            this.isChecked = isChecked
            refreshDrawableState()
        }
    }

    override fun toggle() {
        setChecked(!isChecked)
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked()) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }


}