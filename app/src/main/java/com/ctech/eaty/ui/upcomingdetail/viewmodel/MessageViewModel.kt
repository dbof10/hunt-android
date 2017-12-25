package com.ctech.eaty.ui.upcomingdetail.viewmodel

import android.graphics.Color

class MessageViewModel(val type: Int, val content: String, val color: Int = Color.TRANSPARENT) {
    companion object {
        val TYPE_DEFAULT = 1
        val TYPE_EXTENDED = 2
    }
}