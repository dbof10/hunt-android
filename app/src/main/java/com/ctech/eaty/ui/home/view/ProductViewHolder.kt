package com.ctech.eaty.ui.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.entity.Product
import com.ctech.eaty.util.GlideImageLoader
import vn.tiki.noadapter2.AbsViewHolder


class ProductViewHolder(view: View, val imageLoader: GlideImageLoader) : AbsViewHolder(view) {

    @BindView(R.id.ivProduct)
    lateinit var ivProduct: ImageView

    @BindView(R.id.tvProductName)
    lateinit var tvProductName: TextView

    @BindView(R.id.tvDescription)
    lateinit var tvDescription: TextView

    @BindView(R.id.tvUpvote)
    lateinit var tvUpvote: TextView

    @BindView(R.id.tvCommentCount)
    lateinit var tvCommentCount: TextView

    @BindView(R.id.flProductHolder)
    lateinit var flProductHoder: View

    @BindView(R.id.ivMenu)
    lateinit var ivMenu: View

    @BindView(R.id.tvComment)
    lateinit var tvComment: TextView

    init {
        ButterKnife.bind(this, view)
        flProductHoder.setOnClickListener(this)
        ivMenu.setOnClickListener(this)
        tvComment.setOnClickListener(this)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            return ProductViewHolder(view, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val product = item as Product
        with(product) {
            tvProductName.text = name
            tvDescription.text = tagline
            tvUpvote.text = votesCount.toString()
            tvCommentCount.text = commentsCount.toString()
            imageLoader.downloadInto(imageUrl.smallImgUrl, ivProduct)
        }


    }
}