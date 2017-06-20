package com.ctech.eaty.widget;

import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.ColorInt
import android.support.v7.widget.RecyclerView

class InsetDividerDecoration(val dividedViewHolderClass: Class<Any>,
                             val dividerHeight: Int,
                             val leftInset: Int,
                             @ColorInt val dividerColor: Int) : RecyclerView.ItemDecoration() {

    private var paint: Paint = Paint()

    init {
        paint.color = dividerColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = dividerHeight.toFloat()
    }


    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        if (childCount < 2) {
            return
        }

        val lm = parent.layoutManager
        val lines = FloatArray(childCount * 4)
        var hasDividers = false

        for (i in 0..childCount - 1 ) {

            val child = parent.getChildAt(i)
            val viewHolder = parent.getChildViewHolder(child)

            if (viewHolder.javaClass == dividedViewHolderClass) {
                // skip if this *or next* view is activated
                if (child.isActivated
                        || (i + 1 < childCount && parent.getChildAt(i + 1).isActivated)) {
                    continue
                }
                lines[i * 4] = leftInset + lm.getDecoratedLeft(child).toFloat()
                lines[(i * 4) + 2] = lm.getDecoratedRight(child).toFloat()
                val y = lm.getDecoratedBottom(child) + child.translationY - dividerHeight;
                lines[(i * 4) + 1] = y
                lines[(i * 4) + 3] = y
                hasDividers = true
            }
        }
        if (hasDividers) {
            canvas.drawLines(lines, paint)
        }
    }
}