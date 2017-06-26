package com.ctech.eaty.util

import android.text.SpannableStringBuilder
import android.support.annotation.ColorInt
import android.content.res.ColorStateList
import android.os.Build
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.os.Build.VERSION_CODES
import android.os.Build.VERSION
import android.text.Html
import android.text.Spanned
import android.text.style.URLSpan
import android.text.SpannableString
import com.ctech.eaty.widget.TouchableUrlSpan
import com.ctech.eaty.widget.LinkTouchMovementMethod
import android.widget.TextView


class HtmlUtils private constructor() {

    companion object {

        fun parseHtml(input: String, linkTextColor: ColorStateList, @ColorInt linkHighlightColor: Int): SpannableStringBuilder {
            var spanned = fromHtml(input)

            // strip any trailing newlines
            while (spanned.get(spanned.length - 1) == '\n') {
                spanned = spanned.delete(spanned.length - 1, spanned.length)
            }

            return linkifyLinks(spanned, linkTextColor, linkHighlightColor)
        }

        fun setTextWithNiceLinks(textView: TextView, input: CharSequence) {
            textView.text = input
            textView.movementMethod = LinkTouchMovementMethod.getInstance()
            textView.isFocusable = false
            textView.isClickable = false
            textView.isLongClickable = false
        }

        fun fromHtml(input: String): SpannableStringBuilder {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY) as SpannableStringBuilder
            } else {
                return Html.fromHtml(input) as SpannableStringBuilder
            }
        }

        private fun linkifyLinks(
                input: CharSequence,
                linkTextColor: ColorStateList,
                @ColorInt linkHighlightColor: Int): SpannableStringBuilder {
            val plainLinks = SpannableString(input) // copy of input

            // Linkify doesn't seem to work as expected on M+
            // TODO: figure out why
            //Linkify.addLinks(plainLinks, Linkify.WEB_URLS);

            val urlSpans = plainLinks.getSpans(0, plainLinks.length, URLSpan::class.java)

            // add any plain links to the output
            val ssb = SpannableStringBuilder(input)
            for (urlSpan in urlSpans) {
                ssb.removeSpan(urlSpan)
                ssb.setSpan(TouchableUrlSpan(urlSpan.url, linkTextColor, linkHighlightColor),
                        plainLinks.getSpanStart(urlSpan),
                        plainLinks.getSpanEnd(urlSpan),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            return ssb
        }
    }
}