package com.ctech.eaty.ui.comment.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.entity.Comment
import com.ctech.eaty.util.ImageLoader
import vn.tiki.noadapter2.AbsViewHolder


class CommentViewHolder(view: View, val imageLoader: ImageLoader) : AbsViewHolder(view) {

    @BindView(R.id.ivAvatar)
    lateinit var ivAvatar: ImageView

    @BindView(R.id.tvUserName)
    lateinit var tvUserName: TextView

    @BindView(R.id.tvHeadline)
    lateinit var tvHeadline: TextView

    @BindView(R.id.ivComment)
    lateinit var ivComment: TextView

    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: ImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
            return CommentViewHolder(view, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val comment = item as Comment
        with(comment) {
            tvUserName.text = "${user.username} - ${user.name} "
            tvHeadline.text = user.headline
            ivComment.text = body
            imageLoader.downloadInto(user.imageUrl.smallImgUrl, ivAvatar)
        }


    }
}