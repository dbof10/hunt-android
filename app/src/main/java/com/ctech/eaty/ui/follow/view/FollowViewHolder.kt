package com.ctech.eaty.ui.follow.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.entity.User
import com.ctech.eaty.util.glide.GlideImageLoader
import vn.tiki.noadapter2.AbsViewHolder


class FollowViewHolder(view: View, private val imageLoader: GlideImageLoader) : AbsViewHolder(view) {

    @BindView(R.id.ivAvatar)
    lateinit var ivAvatar: ImageView

    @BindView(R.id.tvUserName)
    lateinit var tvUserName: TextView

    @BindView(R.id.tvHeadline)
    lateinit var tvHeadline: TextView

    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_follow_user, parent, false)
            return FollowViewHolder(view, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val user = item as User
        with(user) {
            tvUserName.text = username
            tvHeadline.text = headline
            imageLoader.downloadInto(imageUrl.px48, ivAvatar)
        }


    }
}