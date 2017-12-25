package com.ctech.eaty.ui.noti.view

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.entity.Notification
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.util.DateUtils
import com.ctech.eaty.util.glide.GlideImageLoader
import vn.tiki.noadapter2.AbsViewHolder


class NotificationViewHolder(view: View, private val imageLoader: GlideImageLoader,
                             private val trackManager: FirebaseTrackManager) : AbsViewHolder(view) {


    @BindView(R.id.ivAvatar)
    lateinit var ivAvatar: ImageView

    @BindView(R.id.tvSentence)
    lateinit var tvSentence: TextView

    @BindView(R.id.ivType)
    lateinit var ivType: ImageView

    @BindView(R.id.tvTimeStamp)
    lateinit var tvTimeStamp: TextView

    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader, trackManager: FirebaseTrackManager): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
            return NotificationViewHolder(view, imageLoader, trackManager)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val notification = item as Notification

        with(notification) {
            val context = itemView.context
            imageLoader.downloadInto(user.imageUrl.px48, ivAvatar)
            val formattedSentence = SpannableStringBuilder(sentence)
            formattedSentence.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_primary_dark)), 0, user.name.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            formattedSentence.setSpan(StyleSpan(Typeface.BOLD), 0, user.name.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            tvSentence.text = formattedSentence
            tvTimeStamp.text = DateUtils.getRelativeTimeSpan(context, createdAt)
            if (!seen) {
                itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorHighlight))
            }
            when (type) {
                "Recommendation" -> {
                    ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_type_recommendation))
                }
                else -> {
                    trackManager.trackNotificationType(type)
                }
            }
        }


    }
}