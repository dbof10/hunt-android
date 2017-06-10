package com.ctech.eaty.util

import android.support.annotation.CheckResult
import android.support.annotation.ColorInt
import android.support.annotation.IntRange


class ColorUtils private constructor() {


    companion object {
        @CheckResult
        @ColorInt
        fun modifyAlpha(@ColorInt color: Int, @IntRange(from = 0, to = 255) alpha: Int): Int {
            return color and 0x00ffffff or (alpha shl 24)
        }

    }

}
