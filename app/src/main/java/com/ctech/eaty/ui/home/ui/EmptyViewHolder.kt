package com.ctech.eaty.ui.home.ui

import android.view.View
import android.view.ViewGroup
import vn.tiki.noadapter2.AbsViewHolder

class EmptyViewHolder(view: View) : AbsViewHolder(view) {
    companion object {
        fun create(parent: ViewGroup): AbsViewHolder {
            return EmptyViewHolder(parent)
        }
    }
}