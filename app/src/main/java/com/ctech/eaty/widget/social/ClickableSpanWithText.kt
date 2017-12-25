package com.ctech.eaty.widget.social

import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.widget.TextView

internal class ClickableSpanWithText private constructor(private val span: ClickableSpan, private val text: String) {

    internal fun span(): ClickableSpan {
        return span
    }

    internal fun text(): String {
        return text
    }

    companion object {

        internal fun ofSpan(textView: TextView, span: ClickableSpan?): ClickableSpanWithText {
            val s = textView.text as Spanned
            val text: String
            text = if (span is URLSpan) {
                span.url
            } else {
                val start = s.getSpanStart(span)
                val end = s.getSpanEnd(span)
                s.subSequence(start, end).toString()
            }
            return ClickableSpanWithText(span!!, text)
        }
    }
}