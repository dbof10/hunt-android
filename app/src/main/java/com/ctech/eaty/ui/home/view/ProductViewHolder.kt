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
import vn.tiki.noadapter2.AbsViewHolder


class ProductViewHolder(view: View, val imageLoader: GlideImageLoader) : AbsViewHolder(view) {


    @BindView(R.id.flProductHolder)
    lateinit var flProductHolder: View

    @BindView(R.id.ivProduct)
    lateinit var ivProduct: ImageView

    @BindView(R.id.tvDescription)
    lateinit var tvDescription: TextView

    @BindView(R.id.tvName)
    lateinit var tvName: TextView

    init {
        ButterKnife.bind(this, view)
        flProductHolder.setOnClickListener(this)
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
            tvName.text = name
            tvDescription.text = tagline
            imageLoader.downloadInto(imageUrl, ivProduct)
        }
    }
}