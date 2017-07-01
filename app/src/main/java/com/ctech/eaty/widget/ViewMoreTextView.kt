package com.ctech.eaty.widget;

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.ctech.eaty.R

class ViewMoreTextView(context: Context, attrs: AttributeSet) : TextView(context, attrs) {
    private val ELLIPSIS: String = "... "
    private var listener: OnViewMoreClickListener? = null
    private var maxCollapsedLines = 4
    private var originalText: CharSequence = ""
    private var shouldCollapseText: Boolean
    private var viewMore: String = context.getString(R.string.view_more)

    val viewMoreSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            if (listener != null) {
                listener?.onViewMoreClick(widget)
                return
            }
            maxLines = Integer.MAX_VALUE
            shouldCollapseText = false
            text = originalText
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = ContextCompat.getColor(context, R.color.hunt_links)
        }
    }

    interface OnViewMoreClickListener {
        fun onViewMoreClick(view: View): Unit
    }

    init {
        movementMethod = LinkMovementMethod.getInstance()
        shouldCollapseText = true
    }

    fun setOnViewMoreClickListener(listener: OnViewMoreClickListener) {
        this.listener = listener
    }

    private fun collapseText() {
        val layout = layout
        if (layout != null && layout.lineCount > maxCollapsedLines) {
            val endOffset = layout.getLineEnd(maxCollapsedLines - 1) - viewMore.length
            if (endOffset >= 1) {
                originalText = text
                val newText = createSpannableWithViewMore(originalText.subSequence(0, endOffset))
                shouldCollapseText = false
                text = newText
            }
        }
    }

    override fun setText(text: CharSequence, type: BufferType) {
        super.setText(text, type)
        if (getText() == text || !shouldCollapseText) {
            shouldCollapseText = true
            return
        }
        maxLines = maxCollapsedLines
        viewTreeObserver.addOnPreDrawListener {
            viewTreeObserver.removeOnPreDrawListener(this)
            collapseText()
            true
        }
    }

    private fun createSpannableWithViewMore(text: CharSequence): SpannableString {
        if ((text.length - ELLIPSIS.length) - viewMore.length < 1) {
            return SpannableString(getText())
        }
        val spannableString = SpannableString(text.subSequence(0, (text.length - ELLIPSIS.length) - viewMore.length).toString() + ELLIPSIS + viewMore)
        val length = text.length
        spannableString.setSpan(viewMoreSpan, length - viewMore.length, length, 33)
        return spannableString
    }
}