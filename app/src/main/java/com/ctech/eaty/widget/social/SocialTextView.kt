package com.ctech.eaty.widget.social

import android.content.Context
import android.graphics.Color
import android.support.annotation.ColorInt
import android.text.SpannableString
import android.text.Spanned
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import com.ctech.eaty.R
import com.ctech.eaty.widget.ViewMoreTextView
import java.util.regex.Matcher
import java.util.regex.Pattern


class SocialTextView constructor(c: Context, attrs: AttributeSet? = null) : ViewMoreTextView(c, attrs) {

    /* Mutable properties */
    private val underlineEnabled: Boolean
    private val selectedColor: Int
    private val hashtagColor: Int
    private val mentionColor: Int
    private val phoneColor: Int
    private val emailColor: Int
    private val urlColor: Int


    private val hashtagPattern by lazy {
        Pattern.compile("(?:^|\\s|$)#[\\p{L}0-9_]*")
    }
    private val mentionPattern by lazy {
        Pattern.compile("(?:^|\\s|$|[.])@[\\p{L}0-9_]*")
    }

    var onLinkClickListener: ((linkType: Int, matchedText: String) -> Unit)? = null

    private val flags: Int

    init {

        movementMethod = AccurateMovementMethod.instance

        val a = c.obtainStyledAttributes(attrs, R.styleable.SocialTextView)
        this.flags = a.getInt(R.styleable.SocialTextView_linkModes, -1)
        this.hashtagColor = a.getColor(R.styleable.SocialTextView_hashtagColor, Color.RED)
        this.mentionColor = a.getColor(R.styleable.SocialTextView_mentionColor, Color.RED)
        this.phoneColor = a.getColor(R.styleable.SocialTextView_phoneColor, Color.RED)
        this.emailColor = a.getColor(R.styleable.SocialTextView_emailColor, Color.RED)
        this.urlColor = a.getColor(R.styleable.SocialTextView_urlColor, Color.RED)
        this.selectedColor = a.getColor(R.styleable.SocialTextView_selectedColor, Color.LTGRAY)
        this.underlineEnabled = a.getBoolean(R.styleable.SocialTextView_underlineEnabled, false)
        if (a.hasValue(R.styleable.SocialTextView_android_text)) {
            setLinkText(a.getString(R.styleable.SocialTextView_android_text))
        }
        if (a.hasValue(R.styleable.SocialTextView_android_hint)) {
            setLinkHint(a.getString(R.styleable.SocialTextView_android_hint))
        }
        a.recycle()
    }

    /**
     * Overridden to ensure that highlighted text is always transparent.
     */
    override fun setHighlightColor(@ColorInt color: Int) {
        super.setHighlightColor(Color.TRANSPARENT)
    }

    fun setLinkText(text: String?) {
        setText(createSocialMediaSpan(text))
    }

    fun appendLinkText(text: String) {
        append(createSocialMediaSpan(text))
    }

    fun setLinkHint(textHint: String?) {
        hint = createSocialMediaSpan(textHint)
    }


    private fun createSocialMediaSpan(text: String?): SpannableString {
        val items = collectLinkItemsFromText(text)
        val textSpan = SpannableString(text)

        for (item in items) {
            textSpan.setSpan(object : TouchableSpan(getColorByMode(item.mode), selectedColor, underlineEnabled) {
                override fun onClick(widget: View) {
                    onLinkClickListener?.invoke(item.mode, item.matched)
                }
            }, item.start, item.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return textSpan
    }


    private fun collectLinkItemsFromText(text: String?): Set<LinkItem> {
        val items = HashSet<LinkItem>()

        if (flags and HASHTAG == HASHTAG) {
            collectLinkItems(HASHTAG, items, hashtagPattern.matcher(text))
        }

        if (flags and MENTION == MENTION) {
            collectLinkItems(MENTION, items, mentionPattern.matcher(text))
        }

        if (flags and PHONE == PHONE) {
            collectLinkItems(PHONE, items, Patterns.PHONE.matcher(text!!))
        }

        if (flags and EMAIL == EMAIL) {
            collectLinkItems(EMAIL, items, Patterns.EMAIL_ADDRESS.matcher(text!!))
        }

        if (flags and URL == URL) {
            collectLinkItems(URL, items, Patterns.WEB_URL.matcher(text!!))
        }

        return items
    }

    private fun collectLinkItems(@LinkOptions mode: Int, items: MutableCollection<LinkItem>, matcher: Matcher) {
        while (matcher.find()) {
            items.add(LinkItem(
                    matcher.group(),
                    matcher.start(),
                    matcher.end(),
                    mode
            ))
        }
    }


    private fun getColorByMode(@LinkOptions mode: Int): Int {
        return when (mode) {
            HASHTAG -> hashtagColor
            MENTION -> mentionColor
            PHONE -> phoneColor
            EMAIL -> emailColor
            URL -> urlColor
            else -> throw IllegalArgumentException("Invalid mode!")
        }
    }


    private data class LinkItem(val matched: String, val start: Int, val end: Int, val mode: Int)


}