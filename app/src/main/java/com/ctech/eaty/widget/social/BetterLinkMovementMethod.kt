package com.ctech.eaty.widget.social

import android.app.Activity
import android.graphics.RectF
import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.util.Linkify
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView


class BetterLinkMovementMethod private constructor() : LinkMovementMethod() {

    private var onLinkClickListener: OnLinkClickListener? = null
    private var onLinkLongClickListener: OnLinkLongClickListener? = null
    private val touchedLineBounds = RectF()
    private var isUrlHighlighted: Boolean = false
    private var clickableSpanUnderTouchOnActionDown: ClickableSpan? = null
    private var activeTextViewHashcode: Int = 0
    private var ongoingLongPressTimer: LongPressTimer? = null
    private var wasLongPressRegistered: Boolean = false


    fun setOnLinkClickListener(clickListener: OnLinkClickListener): BetterLinkMovementMethod {
        if (this == singleInstance) {
            throw UnsupportedOperationException("Setting a click listener on the instance returned by getInstance() is not supported to avoid memory " + "leaks. Please use newInstance() or any of the linkify() methods instead.")
        }

        this.onLinkClickListener = clickListener
        return this
    }


    fun setOnLinkLongClickListener(longClickListener: OnLinkLongClickListener): BetterLinkMovementMethod {
        if (this == singleInstance) {
            throw UnsupportedOperationException("Setting a long-click listener on the instance returned by getInstance() is not supported to avoid " + "memory leaks. Please use newInstance() or any of the linkify() methods instead.")
        }

        this.onLinkLongClickListener = longClickListener
        return this
    }

    override fun onTouchEvent(textView: TextView, text: Spannable, event: MotionEvent): Boolean {
        if (activeTextViewHashcode != textView.hashCode()) {
            // Bug workaround: TextView stops calling onTouchEvent() once any URL is highlighted.
            // A hacky solution is to reset any "autoLink" property set in XML. But we also want
            // to do this once per TextView.
            activeTextViewHashcode = textView.hashCode()
            textView.autoLinkMask = 0
        }

        val clickableSpanUnderTouch = findClickableSpanUnderTouch(textView, text, event)
        val touchStartedOverALink = clickableSpanUnderTouchOnActionDown != null

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (clickableSpanUnderTouch != null) {
                    highlightUrl(textView, clickableSpanUnderTouch, text)
                }

                if (touchStartedOverALink && onLinkLongClickListener != null) {
                    val longClickListener = object : LongPressTimer.OnTimerReachedListener {
                        override fun onTimerReached() {
                            wasLongPressRegistered = true
                            textView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                            removeUrlHighlightColor(textView)
                            dispatchUrlLongClick(textView, clickableSpanUnderTouch)
                        }
                    }
                    startTimerForRegisteringLongClick(textView, longClickListener)
                }

                clickableSpanUnderTouchOnActionDown = clickableSpanUnderTouch
                return touchStartedOverALink
            }

            MotionEvent.ACTION_UP -> {
                // Register a click only if the touch started and ended on the same URL.
                if (!wasLongPressRegistered && touchStartedOverALink && clickableSpanUnderTouch == clickableSpanUnderTouchOnActionDown) {
                    dispatchUrlClick(textView, clickableSpanUnderTouch!!)
                }
                cleanupOnTouchUp(textView)

                // Consume this event even if we could not find any spans to avoid letting Android handle this event.
                // Android's TextView implementation has a bug where links get clicked even when there is no more text
                // next to the link and the touch lies outside its bounds in the same direction.
                return touchStartedOverALink
            }

            MotionEvent.ACTION_CANCEL -> {
                cleanupOnTouchUp(textView)
                return false
            }

            MotionEvent.ACTION_MOVE -> {
                // Stop listening for a long-press as soon as the user wanders off to unknown lands.
                if (clickableSpanUnderTouch !== clickableSpanUnderTouchOnActionDown) {
                    removeLongPressCallback(textView)
                }

                if (!wasLongPressRegistered) {
                    // Toggle highlight.
                    if (clickableSpanUnderTouch != null) {
                        highlightUrl(textView, clickableSpanUnderTouch, text)
                    } else {
                        removeUrlHighlightColor(textView)
                    }
                }

                return touchStartedOverALink
            }

            else -> return false
        }
    }

    private fun cleanupOnTouchUp(textView: TextView) {
        wasLongPressRegistered = false
        removeUrlHighlightColor(textView)
        removeLongPressCallback(textView)
    }


    private fun findClickableSpanUnderTouch(textView: TextView, text: Spannable, event: MotionEvent): ClickableSpan? {
        // So we need to find the location in text where touch was made, regardless of whether the TextView
        // has scrollable text. That is, not the entire text is currently visible.
        var touchX = event.x.toInt()
        var touchY = event.y.toInt()

        // Ignore padding.
        touchX -= textView.totalPaddingLeft
        touchY -= textView.totalPaddingTop

        // Account for scrollable text.
        touchX += textView.scrollX
        touchY += textView.scrollY

        val layout = textView.layout
        val touchedLine = layout.getLineForVertical(touchY)
        val touchOffset = layout.getOffsetForHorizontal(touchedLine, touchX.toFloat())

        touchedLineBounds.left = layout.getLineLeft(touchedLine)
        touchedLineBounds.top = layout.getLineTop(touchedLine).toFloat()
        touchedLineBounds.right = layout.getLineWidth(touchedLine) + touchedLineBounds.left
        touchedLineBounds.bottom = layout.getLineBottom(touchedLine).toFloat()

        return if (touchedLineBounds.contains(touchX.toFloat(), touchY.toFloat())) {
            // Find a ClickableSpan that lies under the touched area.
            val spans = text.getSpans(touchOffset, touchOffset, ClickableSpan::class.java)
            // No ClickableSpan found under the touched location.
            spans.firstOrNull { it is ClickableSpan }

        } else {
            // Touch lies outside the line's horizontal bounds where no spans should exist.
            null
        }
    }


    private fun highlightUrl(textView: TextView, clickableSpan: ClickableSpan, text: Spannable) {
        if (isUrlHighlighted) {
            return
        }
        isUrlHighlighted = true

        val spanStart = text.getSpanStart(clickableSpan)
        val spanEnd = text.getSpanEnd(clickableSpan)
        text.setSpan(BackgroundColorSpan(textView.highlightColor), spanStart, spanEnd, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        textView.text = text

        Selection.setSelection(text, spanStart, spanEnd)
    }

    private fun removeUrlHighlightColor(textView: TextView) {
        if (!isUrlHighlighted) {
            return
        }
        isUrlHighlighted = false

        val text = textView.text as Spannable

        val highlightSpans = text.getSpans(0, text.length, BackgroundColorSpan::class.java)
        for (highlightSpan in highlightSpans) {
            text.removeSpan(highlightSpan)
        }

        textView.text = text

        Selection.removeSelection(text)
    }

    private fun startTimerForRegisteringLongClick(textView: TextView, longClickListener: LongPressTimer.OnTimerReachedListener) {
        ongoingLongPressTimer = LongPressTimer()
        ongoingLongPressTimer?.setOnTimerReachedListener(longClickListener)
        textView.postDelayed(ongoingLongPressTimer, ViewConfiguration.getLongPressTimeout().toLong())
    }

    private fun removeLongPressCallback(textView: TextView) {
        if (ongoingLongPressTimer != null) {
            textView.removeCallbacks(ongoingLongPressTimer)
            ongoingLongPressTimer = null
        }
    }

    private fun dispatchUrlClick(textView: TextView, clickableSpan: ClickableSpan) {
        val clickableSpanWithText = ClickableSpanWithText.ofSpan(textView, clickableSpan)
        val handled = onLinkClickListener?.invoke(textView, clickableSpanWithText.text())

        if (handled == false) {
            // Let Android handle this click.
            clickableSpanWithText.span().onClick(textView)
        }
    }

    private fun dispatchUrlLongClick(textView: TextView, clickableSpan: ClickableSpan?) {
        val clickableSpanWithText = ClickableSpanWithText.ofSpan(textView, clickableSpan)
        val handled = onLinkLongClickListener?.invoke(textView, clickableSpanWithText.text())

        if (handled == false) {
            // Let Android handle this long click as a short-click.
            clickableSpanWithText.span().onClick(textView)
        }
    }

    class LongPressTimer : Runnable {
        private var onTimerReachedListener: OnTimerReachedListener? = null

        interface OnTimerReachedListener {
            fun onTimerReached()
        }

        override fun run() {
            onTimerReachedListener?.onTimerReached()
        }

        fun setOnTimerReachedListener(listener: OnTimerReachedListener) {
            onTimerReachedListener = listener
        }
    }


    companion object {

        private var singleInstance: BetterLinkMovementMethod? = null
        private val LINKIFY_NONE = -2


        fun newInstance(): BetterLinkMovementMethod {
            return BetterLinkMovementMethod()
        }


        fun linkify(linkifyMask: Int, vararg textViews: TextView): BetterLinkMovementMethod {
            val movementMethod = newInstance()
            for (textView in textViews) {
                addLinks(linkifyMask, movementMethod, textView)
            }
            return movementMethod
        }

        fun linkifyHtml(vararg textViews: TextView): BetterLinkMovementMethod {
            return linkify(LINKIFY_NONE, *textViews)
        }


        fun linkify(linkifyMask: Int, viewGroup: ViewGroup): BetterLinkMovementMethod {
            val movementMethod = newInstance()
            rAddLinks(linkifyMask, viewGroup, movementMethod)
            return movementMethod
        }


        fun linkifyHtml(viewGroup: ViewGroup): BetterLinkMovementMethod {
            return linkify(LINKIFY_NONE, viewGroup)
        }


        fun linkify(linkifyMask: Int, activity: Activity): BetterLinkMovementMethod {
            // Find the layout passed to setContentView().
            val activityLayout = (activity.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup).getChildAt(0) as ViewGroup

            val movementMethod = newInstance()
            rAddLinks(linkifyMask, activityLayout, movementMethod)
            return movementMethod
        }


        fun linkifyHtml(activity: Activity): BetterLinkMovementMethod {
            return linkify(LINKIFY_NONE, activity)
        }

        val instance by lazy {
            BetterLinkMovementMethod()
        }

        // ======== PUBLIC APIs END ======== //

        private fun rAddLinks(linkifyMask: Int, viewGroup: ViewGroup, movementMethod: BetterLinkMovementMethod) {
            (0 until viewGroup.childCount)
                    .map { viewGroup.getChildAt(it) }
                    .forEach {
                        if (it is ViewGroup) {
                            // Recursively find child TextViews.
                            rAddLinks(linkifyMask, it, movementMethod)

                        } else if (it is TextView) {
                            addLinks(linkifyMask, movementMethod, it)
                        }
                    }
        }

        private fun addLinks(linkifyMask: Int, movementMethod: BetterLinkMovementMethod, textView: TextView) {
            textView.movementMethod = movementMethod
            if (linkifyMask != LINKIFY_NONE) {
                Linkify.addLinks(textView, linkifyMask)
            }
        }
    }
}