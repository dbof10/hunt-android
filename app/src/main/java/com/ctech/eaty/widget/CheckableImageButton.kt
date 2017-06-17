package com.ctech.eaty.widget;

import android.content.Context
import android.util.AttributeSet
import android.view.SoundEffectConstants
import android.widget.Checkable
import android.widget.ImageView

class CheckableImageButton(context: Context, attrs: AttributeSet) : ImageView(context, attrs), Checkable {

    private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)

    private var isChecked = false

    override fun isChecked() = isChecked

    override fun setChecked(isChecked: Boolean) {
        if (this.isChecked != isChecked) {
            this.isChecked = isChecked
            refreshDrawableState()
        }
    }

    override fun toggle() {
        setChecked(!isChecked)
    }

    override fun performClick(): Boolean {
        toggle()
        val handled = super.performClick()
        if (!handled) {
            playSoundEffect(SoundEffectConstants.CLICK)
        }
        return handled
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }
}