package com.ctech.eaty.widget.recyclerview

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class HorizontalSpaceItemDecoration(private val dividedViewHolderClass: Class<out Any>, private val dividerHeight: Int) : RecyclerView.ItemDecoration() {

    override
    fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val viewHolder = parent.getChildViewHolder(view)
        if (viewHolder.javaClass == dividedViewHolderClass) {
            outRect.right = dividerHeight
        }
    }
}