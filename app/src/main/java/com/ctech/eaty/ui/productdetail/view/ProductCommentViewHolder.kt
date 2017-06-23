package com.ctech.eaty.ui.productdetail.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.productdetail.viewmodel.CommentItemViewModel
import com.ctech.eaty.util.DateUtil
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.widget.ViewMoreTextView
import vn.tiki.noadapter2.AbsViewHolder

class ProductCommentViewHolder(view: View, val imageLoader: GlideImageLoader) : AbsViewHolder(view), ViewMoreTextView.OnViewMoreClickListener {

    @BindView(R.id.ivAvatar)
    lateinit var ivAvatar: ImageView

    @BindView(R.id.tvUserName)
    lateinit var tvUserName: TextView

    @BindView(R.id.tvComment)
    lateinit var tvComment: ViewMoreTextView

    @BindView(R.id.tvReplyCount)
    lateinit var tvReplyCount: TextView

    @BindView(R.id.ivCommentLike)
    lateinit var ivCommentLike: ImageView

    @BindView(R.id.ivReply)
    lateinit var ivReply: ImageView

    @BindView(R.id.tvTimeStamp)
    lateinit var tvTimeStamp: TextView

    init {
        ButterKnife.bind(this, view)
        tvComment.setOnViewMoreClickListener(this)

    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_comment, parent, false)
            return ProductCommentViewHolder(view, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val comment = item as CommentItemViewModel
        with(comment) {
            itemView.isActivated = isSelected
            tvReplyCount.visibility = replyCountVisibility
            ivCommentLike.visibility = commentLikeVisibility
            ivReply.visibility = replyVisibility
            tvUserName.text = userName
            tvComment.text = body
            tvTimeStamp.text = DateUtil.getRelativeTimeSpan(itemView.context, comment.createdAt)
            imageLoader.downloadInto(imageUrl, ivAvatar)
        }


    }
}