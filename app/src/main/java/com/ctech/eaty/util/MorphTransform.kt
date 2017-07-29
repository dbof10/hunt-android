package com.ctech.eaty.util

import android.content.Intent
import android.support.annotation.ColorInt
import android.transition.ChangeBounds


class MorphTransform : ChangeBounds() {


    companion object {

        private val EXTRA_SHARED_ELEMENT_START_COLOR = "EXTRA_SHARED_ELEMENT_START_COLOR"
        private val EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS = "EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS"

        fun addExtras(intent: Intent, @ColorInt startColor: Int,
                      startCornerRadius: Int) {
            intent.putExtra(EXTRA_SHARED_ELEMENT_START_COLOR, startColor)
            intent.putExtra(EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS, startCornerRadius)
        }
    }
}