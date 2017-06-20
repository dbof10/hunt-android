package com.ctech.eaty.ui.productdetail.view

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.productdetail.viewmodel.ProductHeaderItemViewModel
import com.ctech.eaty.util.GlideImageLoader
import vn.tiki.noadapter2.AbsViewHolder

class ProductHeaderViewHolder(view: View, val imageLoader: GlideImageLoader) : AbsViewHolder(view) {

    @BindView(R.id.btVote)
    lateinit var btVote: Button

    @BindView(R.id.btCommentCount)
    lateinit var btCommentCount: Button

    @BindView(R.id.btShare)
    lateinit var btShare: Button

    @BindView(R.id.tvHunterName)
    lateinit var tvHunterName: TextView

    @BindView(R.id.ivHunterAvatar)
    lateinit var ivHunterAvatar: ImageView

    @BindView(R.id.tvProductTitle)
    lateinit var tvProductTitle: TextView

    @BindView(R.id.tvProductDescription)
    lateinit var tvProductDescription: TextView

    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(header: View, imageLoader: GlideImageLoader): AbsViewHolder {
            return ProductHeaderViewHolder(header, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val header = item as ProductHeaderItemViewModel
        with(header) {
            btVote.text = voteCount.toString()
            btCommentCount.text = commentCount.toString()
            tvHunterName.text = hunterName
            tvProductTitle.text = productTitle
            tvProductDescription.text = productDescription
            imageLoader.downloadInto(avatarUrl, ivHunterAvatar)
        }


    }
}