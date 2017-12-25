package com.ctech.eaty.ui.upcomingdetail.view

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.OnClick
import com.ctech.eaty.R
import com.ctech.eaty.ui.upcomingdetail.viewmodel.MessageViewModel


class MessageExtendedViewHolder private constructor(view: View) : MessageViewHolder(view) {

    @BindView(R.id.etEmail)
    lateinit var etEmail: EditText

    @BindView(R.id.btSubscribe)
    lateinit var btSubscribe: Button

    private lateinit var textWatcher: TextWatcher
    internal var emailSubmit: OnEmailSubmitListener? = null

    companion object {
        fun create(viewGroup: ViewGroup): MessageExtendedViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_upcoming_detail_extended_message, viewGroup, false)
            return MessageExtendedViewHolder(view)
        }
    }

    init {
        textWatcher = object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                val text = editable.toString()
                if (text.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    etEmail.error = null
                    btSubscribe.isEnabled = true
                } else {
                    etEmail.error = itemView.context.getString(R.string.invalid_email)
                    btSubscribe.isEnabled = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            }

        }
    }

    @OnClick(R.id.btSubscribe)
    fun onSubmit() {
        emailSubmit?.invoke(etEmail.text.toString())
    }

    override fun bind(viewModel: MessageViewModel) {
        super.bind(viewModel)
        val buttonRadius = itemView.context.resources.getDimensionPixelSize(R.dimen.button_radius).toFloat()
        with(viewModel) {
            val csl = ColorStateList(arrayOf(intArrayOf()), intArrayOf(viewModel.color))
            etEmail.backgroundTintList = csl

            val shape = GradientDrawable()
            shape.shape = GradientDrawable.RECTANGLE
            shape.cornerRadii = floatArrayOf(buttonRadius, buttonRadius, buttonRadius, buttonRadius, buttonRadius, buttonRadius, buttonRadius, buttonRadius)
            shape.setColor(color)
            btSubscribe.background = shape
        }
        etEmail.addTextChangedListener(textWatcher)
    }

    override fun onUnbind() {
        super.onUnbind()
        etEmail.removeTextChangedListener(textWatcher)
    }
}