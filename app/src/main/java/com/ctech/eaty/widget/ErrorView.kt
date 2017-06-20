package com.ctech.eaty.widget

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R

class ErrorView : LinearLayout {
    val ALIGNMENT_LEFT = 0
    val ALIGNMENT_CENTER = 1
    val ALIGNMENT_RIGHT = 2

    @BindView(R.id.ivError)
    lateinit var ivError: ImageView
    @BindView(R.id.tvReason)
    lateinit var tvReason: TextView
    @BindView(R.id.tvExplain)
    lateinit var tvExplain: TextView
    @BindView(R.id.tvRetry)
    lateinit var tvRetry: TextView

    private lateinit var retry: () -> Unit

    constructor(context: Context) : this(context, null)


    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.style)


    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : this(context, attrs, defStyle, 0)


    constructor(context: Context, attrs: AttributeSet?, defStyle: Int, defStyleRes: Int) : super(context, attrs) {
        init(attrs, defStyle, defStyleRes)
    }


    private fun init(attrs: AttributeSet?, defStyle: Int, defStyleRes: Int) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ErrorView, defStyle, defStyleRes)

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_error, this, true)
        orientation = VERTICAL
        gravity = Gravity.CENTER

        layoutTransition = LayoutTransition()

        ButterKnife.bind(this)

        val imageRes: Int

        val reason: String?
        val reasonColor: Int

        val explain: String?
        val explainColor: Int

        val showReason: Boolean
        val showExplain: Boolean
        val showRetryButton: Boolean

        val retryButtonText: String?
        val retryButtonBackground: Int
        val retryButtonTextColor: Int

        try {
            imageRes = a.getResourceId(R.styleable.ErrorView_errorImage, 0)
            reason = a.getString(R.styleable.ErrorView_reason)
            reasonColor = a.getColor(R.styleable.ErrorView_reasonColor,
                    ContextCompat.getColor(context, R.color.error_view_text))
            explain = a.getString(R.styleable.ErrorView_explain)
            explainColor = a.getColor(R.styleable.ErrorView_explainColor,
                    ContextCompat.getColor(context, R.color.error_view_text_light))
            showReason = a.getBoolean(R.styleable.ErrorView_showReason, true)
            showExplain = a.getBoolean(R.styleable.ErrorView_showExplain, true)
            showRetryButton = a.getBoolean(R.styleable.ErrorView_showRetryButton, true)
            retryButtonText = a.getString(R.styleable.ErrorView_retryButtonText)
            retryButtonBackground = a.getResourceId(R.styleable.ErrorView_retryButtonBackground, 0)
            retryButtonTextColor = a.getColor(R.styleable.ErrorView_retryButtonTextColor,
                    ContextCompat.getColor(context, R.color.error_view_text_dark))
            val alignInt = a.getInt(R.styleable.ErrorView_explainAlignment, 1)

            if (imageRes != 0) {
                setImage(imageRes)
            }

            if (reason != null) {
                setReason(reason)
            }

            if (explain != null) {
                setExplain(explain)
            }

            if (retryButtonText != null) {
                tvRetry.text = retryButtonText
            }

            if (!showReason) {
                tvReason.visibility = GONE
            }

            if (!showExplain) {
                tvExplain.visibility = GONE
            }

            if (!showRetryButton) {
                tvRetry.visibility = GONE
            }

            tvReason.setTextColor(reasonColor)
            tvExplain.setTextColor(explainColor)

            tvRetry.setTextColor(retryButtonTextColor)

            if (retryButtonBackground != 0)
                tvRetry.setBackgroundResource(retryButtonBackground)

            setExplainAlignment(alignInt)
        } finally {
            a.recycle()
        }

        tvRetry.setOnClickListener {
            retry()
        }
    }

    fun setOnRetryListener(retry: () -> Unit) {
        this.retry = retry
    }


    fun setImage(res: Int) {
        ivError.setImageResource(res)
    }

    fun setImage(drawable: Drawable) {
        ivError.setImageDrawable(drawable)
    }

    fun setImage(bitmap: Bitmap) {
        ivError.setImageBitmap(bitmap)
    }

    fun getImage(): Drawable {
        return ivError.drawable
    }

    fun setReason(text: String) {
        tvReason.text = text
    }

    fun setReason(res: Int) {
        tvReason.setText(res)
    }

    fun getReason(): String {
        return tvReason.text.toString()
    }

    fun setReasonColor(res: Int) {
        tvReason.setTextColor(res)
    }

    fun getReasonColor(): Int {
        return tvReason.currentTextColor
    }

    fun setExplain(exception: String) {
        tvExplain.text = exception
    }

    fun setExplain(res: Int) {
        tvExplain.setText(res)
    }

    fun getExplain(): String {
        return tvExplain.text.toString()
    }

    fun setExplainColor(res: Int) {
        tvExplain.setTextColor(res)
    }

    fun getSubtitleColor(): Int {
        return tvExplain.currentTextColor
    }

    fun setRetryButtonText(text: String) {
        tvRetry.text = text
    }

    fun setRetryButtonText(res: Int) {
        tvRetry.setText(res)
    }

    fun getRetryButtonText(): String {
        return tvRetry.text.toString()
    }

    fun setRetryButtonTextColor(color: Int) {
        tvRetry.setTextColor(color)
    }

    fun getRetryButtonTextColor(): Int {
        return tvRetry.currentTextColor
    }

    fun showTitle(show: Boolean) {
        tvReason.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun isReasonVisible(): Boolean {
        return tvReason.visibility == VISIBLE
    }

    fun showExplain(show: Boolean) {
        tvExplain.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun isExplainVisible(): Boolean {
        return tvExplain.visibility == VISIBLE
    }

    fun showRetryButton(show: Boolean) {
        tvRetry.visibility = if (show) VISIBLE else GONE
    }

    fun isRetryButtonVisible(): Boolean {
        return tvRetry.visibility == VISIBLE
    }

    fun setExplainAlignment(alignment: Int) {
        if (alignment == ALIGNMENT_LEFT) {
            tvExplain.gravity = Gravity.START
        } else if (alignment == ALIGNMENT_CENTER) {
            tvExplain.gravity = Gravity.CENTER_HORIZONTAL
        } else {
            tvExplain.gravity = Gravity.END
        }
    }

    fun getExplainAlignment(): Int {
        val gravity = tvExplain.gravity
        if (gravity == Gravity.START) {
            return ALIGNMENT_LEFT
        } else if (gravity == Gravity.CENTER_HORIZONTAL) {
            return ALIGNMENT_CENTER
        } else {
            return ALIGNMENT_RIGHT
        }
    }

}