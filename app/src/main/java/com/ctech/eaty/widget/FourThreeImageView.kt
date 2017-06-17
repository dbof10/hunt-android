package com.ctech.eaty.widget;

import android.content.Context
import android.util.AttributeSet

open class FourThreeImageView(context: Context, attrs: AttributeSet) : ForegroundImageView(context, attrs) {

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val fourThreeHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthSpec) * 3 / 4,
                MeasureSpec.EXACTLY)
        super.onMeasure(widthSpec, fourThreeHeight)
    }
}