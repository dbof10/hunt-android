package com.ctech.eaty.util

import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.text.style.URLSpan
import android.util.Log
import android.view.View
import com.ctech.eaty.widget.social.OnImageClickListener


object HtmlUtils {


    fun fromHtml(input: String, imageGetter: Html.ImageGetter? = null): SpannableStringBuilder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY, imageGetter, null) as SpannableStringBuilder
        } else {
            Html.fromHtml(input, imageGetter, null) as SpannableStringBuilder
        }
    }

    fun setClickListenerOnHtmlImageGetter(html: Spannable, listener: OnImageClickListener) {
        for (span in html.getSpans(0, html.length, ImageSpan::class.java)) {
            val flags = html.getSpanFlags(span)
            val start = html.getSpanStart(span)
            val end = html.getSpanEnd(span)

            html.setSpan(object : URLSpan(span.source) {
                override fun onClick(v: View) {
                    listener.invoke(v, span.source)
                }
            }, start, end, flags)
        }
    }
}