package com.ctech.eaty.ui.upcomingdetail.view

import android.support.v7.widget.RecyclerView
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.upcomingdetail.viewmodel.MessageViewModel
import com.ctech.eaty.util.HtmlUtils
import com.ctech.eaty.util.glide.GlideImageGetter
import com.ctech.eaty.widget.social.BetterLinkMovementMethod
import com.ctech.eaty.widget.social.OnLinkClickListener

open class MessageViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {


    @BindView(R.id.tvMessage)
    lateinit var tvMessage: TextView

    private val imageGetter: GlideImageGetter

    internal var linkListener: OnLinkClickListener? = null

    companion object {
        fun create(viewGroup: ViewGroup): MessageViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_upcoming_detail_message, viewGroup, false)
            return MessageViewHolder(view)
        }
    }

    init {
        ButterKnife.bind(this, view)
        imageGetter = GlideImageGetter(tvMessage)
        BetterLinkMovementMethod
                .linkify(Linkify.ALL, tvMessage)
                .setOnLinkClickListener { textView, url ->
                    linkListener?.invoke(textView, url) ?: false
                }

    }


    open fun bind(viewModel: MessageViewModel) {

        itemView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                itemView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                imageGetter.setHolderWidth(itemView.width)
                imageGetter.setHolderHeight(itemView.height)
                val spannable = HtmlUtils.fromHtml(viewModel.content, imageGetter)
                HtmlUtils.setClickListenerOnHtmlImageGetter(spannable) { _, _ ->
                }
                tvMessage.text = spannable
            }
        })
    }

    open fun onUnbind() {
        imageGetter.clear()
    }

}