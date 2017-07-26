package com.ctech.eaty.ui.vote.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.entity.Vote
import com.ctech.eaty.util.DateUtils
import com.ctech.eaty.util.GlideImageLoader
import vn.tiki.noadapter2.AbsViewHolder


class VoteViewHolder(view: View, private val imageLoader: GlideImageLoader) : AbsViewHolder(view) {

    @BindView(R.id.ivAvatar)
    lateinit var ivAvatar: ImageView

    @BindView(R.id.tvUserName)
    lateinit var tvUserName: TextView

    @BindView(R.id.tvHeadline)
    lateinit var tvHeadline: TextView

    @BindView(R.id.tvTimeStamp)
    lateinit var tvTimeStamp: TextView

    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_vote, parent, false)
            return VoteViewHolder(view, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val vote = item as Vote
        with(vote) {
            tvUserName.text = user.username
            tvHeadline.text = user.headline
            imageLoader.downloadInto(user.imageUrl.px48, ivAvatar)
            tvTimeStamp.text = DateUtils.getRelativeTimeSpan(itemView.context, vote.createAt)
        }


    }
}