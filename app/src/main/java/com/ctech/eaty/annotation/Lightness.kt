package com.ctech.eaty.annotation

import android.support.annotation.IntDef

const val IS_LIGHT = 0
const val IS_DARK = 1
const val LIGHTNESS_UNKNOWN = 2

@Retention(AnnotationRetention.SOURCE)
@IntDef(IS_LIGHT, IS_DARK, LIGHTNESS_UNKNOWN)
annotation class Lightness