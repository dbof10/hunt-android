package com.ctech.eaty.widget.recyclerview;

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class VerticalSpaceItemDecoration(private val listDividedViewHolderClasses: List<Class<out Any>>,
                                  private val dividerHeight: Int) : RecyclerView.ItemDecoration() {

    constructor (dividedViewHolderClass: Class<out Any>, dividerHeight: Int) : this(listOf(dividedViewHolderClass), dividerHeight)

    override
    fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val viewHolder = parent.getChildViewHolder(view)
        listDividedViewHolderClasses.forEach {
            if (viewHolder.javaClass == it) {
                outRect.bottom = dividerHeight
            }
        }
    }
}