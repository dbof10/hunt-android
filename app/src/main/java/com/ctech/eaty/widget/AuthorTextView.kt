package com.ctech.eaty.widget;

import android.content.Context
import android.util.AttributeSet
import com.ctech.eaty.R;

 class AuthorTextView(context: Context, attrs: AttributeSet) : BaselineGridTextView(context, attrs) {

    private val  STATE_ORIGINAL_POSTER = intArrayOf( R.attr.state_original_poster )

    private var isOP = false


    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isOP) {
            mergeDrawableStates(drawableState, STATE_ORIGINAL_POSTER)
        }
        return drawableState
    }

    fun isOriginalPoster() : Boolean{
        return isOP
    }

    fun setOriginalPoster(isOP: Boolean) {
        if (this.isOP != isOP) {
            this.isOP = isOP
            refreshDrawableState()
        }
    }
}