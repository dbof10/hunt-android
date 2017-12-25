package com.ctech.eaty.util

import android.support.annotation.DimenRes
import android.view.View
import android.view.ViewTreeObserver


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

fun View.setWidth(width: Int) {
    val newLayoutParams = layoutParams
    newLayoutParams.width = width
    layoutParams = newLayoutParams
}

fun View.setHeight(height: Int) {
    val newLayoutParams = layoutParams
    newLayoutParams.height = height
    layoutParams = newLayoutParams
}

inline fun View.onLaidOut(crossinline body: () -> Unit) {
    if (isLaidOut) {
        body()
        return
    }

    val observer = viewTreeObserver
    observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (observer.isAlive) {
                observer
            } else {
                viewTreeObserver
            }.removeOnGlobalLayoutListener(this)

            body()
        }
    })
}