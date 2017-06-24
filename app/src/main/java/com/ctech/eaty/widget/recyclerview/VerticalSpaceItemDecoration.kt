package com.ctech.eaty.widget.recyclerview;

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class VerticalSpaceItemDecoration(private val dividedViewHolderClass: Class<out Any>,
                                  private val dividerHeight: Int) : RecyclerView.ItemDecoration() {

    override
    fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {


        (0..parent.childCount - 1)
                .map { parent.getChildViewHolder(parent.getChildAt(it)) }
                .filter { it.javaClass == dividedViewHolderClass }
                .forEach { outRect.bottom = dividerHeight }
    }
}