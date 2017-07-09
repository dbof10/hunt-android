package com.ctech.eaty.ui.productdetail.view

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

class RelatedProductViewHolder(view: View, private val imageLoader: GlideImageLoader) : AbsViewHolder(view) {

    @BindView(R.id.ivProduct)
    lateinit var ivProduct: ImageView

    @BindView(R.id.tvName)
    lateinit var tvName: TextView

    private val IMAGE_SIZE = 200

    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_related_product, parent, false)
            return RelatedProductViewHolder(view, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val product = item as ProductItemViewModel
        with(product) {
            tvName.text = name
            imageLoader.downloadInto(ResizeImageUrlProvider.overrideUrl(thumbnailUrl, IMAGE_SIZE), ivProduct)
        }
    }
}