package com.ctech.eaty.widget.social

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan


abstract class TouchableSpan(private val normalTextColor: Int, private val pressedTextColor: Int, private val underlineEnabled: Boolean) : ClickableSpan() {
    private var pressed: Boolean = false

    override fun updateDrawState(paint: TextPaint) {
        val textColor = if (pressed) pressedTextColor else normalTextColor
        paint.color = textColor
        paint.isUnderlineText = underlineEnabled
        paint.bgColor = Color.TRANSPARENT
    }

    /**
     * Sets the flag for when the span is pressed.
     * @param value True if pressed
     */
    internal fun setPressed(value: Boolean) {
        this.pressed = value
    }
}