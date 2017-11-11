package com.ctech.eaty.widget

import android.animation.LayoutTransition
import android.content.Context
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
    private val ALIGNMENT_LEFT = 0
    private val ALIGNMENT_CENTER = 1
    private val ALIGNMENT_RIGHT = 2

    @BindView(R.id.ivError)
    lateinit var ivError: ImageView
    @BindView(R.id.tvReason)
    lateinit var tvReason: TextView
    @BindView(R.id.tvExplain)
    lateinit var tvExplain: TextView
    @BindView(R.id.tvRetry)
    lateinit var tvRetry: TextView

    var onRetry: (() -> Unit)? = null

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

        val explain: String?

        val showReason: Boolean
        val showExplain: Boolean
        val showRetryButton: Boolean

        val retryButtonText: String?
        val retryButtonBackground: Int

        try {
            imageRes = a.getResourceId(R.styleable.ErrorView_errorImage, 0)
            reason = a.getString(R.styleable.ErrorView_reason)
            explain = a.getString(R.styleable.ErrorView_explain)
            showReason = a.getBoolean(R.styleable.ErrorView_showReason, true)
            showExplain = a.getBoolean(R.styleable.ErrorView_showExplain, true)
            showRetryButton = a.getBoolean(R.styleable.ErrorView_showRetryButton, true)
            retryButtonText = a.getString(R.styleable.ErrorView_retryButtonText)
            retryButtonBackground = a.getResourceId(R.styleable.ErrorView_retryButtonBackground, 0)
            val alignInt = a.getInt(R.styleable.ErrorView_explainAlignment, 1)

            if (imageRes != 0) {
                setImage(ContextCompat.getDrawable(context, imageRes))
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

            if (retryButtonBackground != 0)
                tvRetry.setBackgroundResource(retryButtonBackground)

            setExplainAlignment(alignInt)
        } finally {
            a.recycle()
        }

        tvRetry.setOnClickListener {
            onRetry?.invoke()
        }
    }

    fun setOnRetryListener(retry: () -> Unit) {
        this.onRetry = retry
    }


    fun setImage(drawable: Drawable) {
        ivError.setImageDrawable(drawable)
    }

    fun setReason(text: String) {
        tvReason.text = text
    }

    fun setExplain(exception: String) {
        tvExplain.text = exception
    }

    fun setRetryButtonText(text: String) {
        tvRetry.text = text
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
        when (alignment) {
            ALIGNMENT_LEFT -> tvExplain.gravity = Gravity.START
            ALIGNMENT_CENTER -> tvExplain.gravity = Gravity.CENTER_HORIZONTAL
            else -> tvExplain.gravity = Gravity.END
        }
    }

    fun getExplainAlignment(): Int {
        val gravity = tvExplain.gravity
        return when (gravity) {
            Gravity.START -> ALIGNMENT_LEFT
            Gravity.CENTER_HORIZONTAL -> ALIGNMENT_CENTER
            else -> ALIGNMENT_RIGHT
        }
    }

}