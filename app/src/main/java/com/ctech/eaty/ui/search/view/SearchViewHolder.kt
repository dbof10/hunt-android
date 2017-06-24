package com.ctech.eaty.ui.search.view;

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

class SearchViewHolder(view: View, val imageLoader: GlideImageLoader) : AbsViewHolder(view) {

    @BindView(R.id.ivProduct)
    lateinit var ivProduct: ImageView

    @BindView(R.id.tvProductName)
    lateinit var tvProductName: TextView

    @BindView(R.id.tvDescription)
    lateinit var tvDescription: TextView

    @BindView(R.id.flProductHolder)
    lateinit var flProductHoder: View


    init {
        ButterKnife.bind(this, view)
        flProductHoder.setOnClickListener(this)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
            return SearchViewHolder(view, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val product = item as ProductItemViewModel
        with(product) {
            tvProductName.text = name
            tvDescription.text = tagline
            imageLoader.downloadInto(imageUrl, ivProduct)
        }


    }
}