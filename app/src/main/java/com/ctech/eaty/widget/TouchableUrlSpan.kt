package com.ctech.eaty.widget;

import android.content.res.ColorStateList;
import android.text.TextPaint;
import android.text.style.URLSpan;

 class TouchableUrlSpan(url: String,
                        textColor: ColorStateList,
                        private val pressedBackgroundColor: Int) : URLSpan(url) {

    private val STATE_PRESSED = IntArray(android.R.attr.state_pressed)
    private var isPressed = false
    private val  normalTextColor = textColor.defaultColor
    private val pressedTextColor =  textColor.getColorForState(STATE_PRESSED, normalTextColor)

     fun setPressed( isPressed: Boolean) {
        this.isPressed = isPressed
    }

    override fun updateDrawState( drawState: TextPaint) {
        drawState.color = if(isPressed) pressedTextColor else normalTextColor
        drawState.bgColor = if(isPressed)  pressedBackgroundColor else 0
        drawState.isUnderlineText = !isPressed
    }
}