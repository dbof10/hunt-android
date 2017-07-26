package com.ctech.eaty.util

import android.view.View


fun View.setPaddingTop(paddingTop: Int) {
   setPaddingRelative(paddingStart,
            paddingTop,
           paddingEnd,
           paddingBottom)
}

fun View.setPaddingBottom(paddingBottom: Int) {
    setPaddingRelative(paddingStart,
            paddingTop,
            paddingEnd,
            paddingBottom)
}