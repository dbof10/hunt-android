package com.ctech.eaty.widget

import android.content.Context
import android.util.AttributeSet
import com.ctech.eaty.util.ViewUtils

class CircularImageView(context: Context, attrs: AttributeSet) : ForegroundImageView(context, attrs) {

    init {
        outlineProvider = ViewUtils.CIRCULAR_OUTLINE
        clipToOutline = true
    }
}
