package com.ctech.eaty.ui.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.util.ResizeImageUrlProvider
import vn.tiki.noadapter2.AbsViewHolder


class ProductViewHolder(view: View, val imageLoader: GlideImageLoader) : AbsViewHolder(view) {

    private val IMAGE_WIDTH = 300

    @BindView(R.id.ivProduct)
    lateinit var ivProduct: ImageView

    @BindView(R.id.tvProductName)
    lateinit var tvProductName: TextView

    @BindView(R.id.tvDescription)
    lateinit var tvDescription: TextView

    @BindView(R.id.llUpvote)
    lateinit var llUpvote: View

    @BindView(R.id.llComment)
    lateinit var llComment: View

    @BindView(R.id.llShare)
    lateinit var llShare: View

    @BindView(R.id.tvUpvote)
    lateinit var tvUpvote: TextView

    @BindView(R.id.tvCommentCount)
    lateinit var tvCommentCount: TextView

    @BindView(R.id.flProductHolder)
    lateinit var flProductHolder: View

    init {
        ButterKnife.bind(this, view)
        flProductHolder.setOnClickListener(this)
        llUpvote.setOnClickListener(this)
        llComment.setOnClickListener(this)
        llShare.setOnClickListener(this)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            return ProductViewHolder(view, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val product = item as ProductItemViewModel
        with(product) {
            tvProductName.text = name
            tvDescription.text = tagline
            tvUpvote.text = votesCount
            tvCommentCount.text = commentsCount
            imageLoader.downloadInto(ResizeImageUrlProvider.getNewUrl(imageUrl, IMAGE_WIDTH), ivProduct)
        }


    }
}