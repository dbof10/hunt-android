package com.ctech.eaty.ui.comment.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.comment.viewmodel.CommentItemViewModel
import com.ctech.eaty.util.DateUtils
import com.ctech.eaty.util.glide.GlideImageLoader
import com.ctech.eaty.widget.ViewMoreTextView
import vn.tiki.noadapter2.AbsViewHolder

class CommentExpandedViewHolder(view: View, val imageLoader: GlideImageLoader) : AbsViewHolder(view), ViewMoreTextView.OnViewMoreClickListener {


    @BindView(R.id.ivAvatar)
    lateinit var ivAvatar: ImageView

    @BindView(R.id.tvUserName)
    lateinit var tvUserName: TextView

    @BindView(R.id.tvHeadline)
    lateinit var tvHeadline: TextView

    @BindView(R.id.tvComment)
    lateinit var tvComment: TextView

    @BindView(R.id.tvTimeStamp)
    lateinit var tvTimeStamp: TextView

    init {
        ButterKnife.bind(this, view)
    }

    override fun onViewMoreClick(view: View) {
        onClick(view)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_expanded, parent, false)
            return CommentExpandedViewHolder(view, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val viewModel = item as CommentItemViewModel
        with(viewModel) {
            tvUserName.text = name
            tvHeadline.text = headline
            tvComment.text = body
            tvTimeStamp.text = DateUtils.getRelativeTimeSpan(itemView.context, createdAt)
            imageLoader.downloadInto(imageUrl, ivAvatar)
        }


    }
}