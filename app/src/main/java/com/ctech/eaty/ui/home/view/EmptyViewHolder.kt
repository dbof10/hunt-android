package com.ctech.eaty.ui.home.view

import android.view.View
import android.view.ViewGroup
import vn.tiki.noadapter2.AbsViewHolder

class EmptyViewHolder(view: View) : AbsViewHolder(view) {
    companion object {
        fun create(parent: ViewGroup): AbsViewHolder {
            return EmptyViewHolder(View(parent.context))
        }
    }
}