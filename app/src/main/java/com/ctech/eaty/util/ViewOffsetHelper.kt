package com.ctech.eaty.util;

import android.support.v4.view.ViewCompat
import android.view.View


class ViewOffsetHelper(private val view: View) {

    companion object {
        val OFFSET_Y = AnimUtils.createIntProperty(
                object : AnimUtils.IntProp<ViewOffsetHelper>("topAndBottomOffset") {
                    override fun set(viewOffsetHelper: ViewOffsetHelper, offset: Int) {
                        viewOffsetHelper.setTopAndBottomOffset(offset)

                    }

                    override fun get(viewOffsetHelper: ViewOffsetHelper): Int = viewOffsetHelper.getTopAndBottomOffset()

                })
    }


    private var layoutTop = 0
    private var layoutLeft = 0
    private var offsetTop = 0
    private var offsetLeft = 0


    fun onViewLayout() {
        layoutTop = view.top
        layoutLeft = view.left

        updateOffsets()
    }

    /**
     * Set the top and bottom offset for this {@link ViewOffsetHelper}'s view by
     * an absolute amount.
     *
     * @param absoluteOffset the offset in px.
     * @return true if the offset has changed
     */
    fun setTopAndBottomOffset(absoluteOffset: Int): Boolean {
        if (offsetTop != absoluteOffset) {
            offsetTop = absoluteOffset
            updateOffsets()
            return true
        }
        return false
    }

    /**
     * Set the top and bottom offset for this {@link ViewOffsetHelper}'s view by
     * an relative amount.
     */
    fun offsetTopAndBottom(relativeOffset: Int) {
        offsetTop += relativeOffset
        updateOffsets()
    }

    fun getTopAndBottomOffset(): Int = offsetTop

    /**
     * Notify this helper that a change to the view's offsets has occurred outside of this class.
     */
    fun resyncOffsets() {
        offsetTop = view.top - layoutTop
        offsetLeft = view.left - layoutLeft
    }

    private fun updateOffsets() {
        ViewCompat.offsetTopAndBottom(view, offsetTop - (view.top - layoutTop))
        ViewCompat.offsetLeftAndRight(view, offsetLeft - (view.left - layoutLeft))
    }

}