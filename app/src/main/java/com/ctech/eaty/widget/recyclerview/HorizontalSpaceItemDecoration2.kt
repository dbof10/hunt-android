package com.ctech.eaty.widget.recyclerview

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class HorizontalSpaceItemDecoration2(private val dividerHeight: Int) : RecyclerView.ItemDecoration() {

    override
    fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = dividerHeight
        if (parent.getChildAdapterPosition(view) == (parent.adapter.itemCount - 1)) {
            outRect.right = dividerHeight
        }
    }
}