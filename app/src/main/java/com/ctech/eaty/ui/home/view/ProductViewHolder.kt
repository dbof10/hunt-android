package com.ctech.eaty.ui.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.ctech.eaty.widget.drawable.CircleProgressBarDrawable
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest
import vn.tiki.noadapter2.AbsViewHolder


class ProductViewHolder(view: View) : AbsViewHolder(view) {


    @BindView(R.id.flProductHolder)
    lateinit var flProductHolder: View

    @BindView(R.id.ivProduct)
    lateinit var ivProduct: SimpleDraweeView

    @BindView(R.id.tvDescription)
    lateinit var tvDescription: TextView

    @BindView(R.id.tvName)
    lateinit var tvName: TextView

    private val thumbNailSize = itemView.resources.getDimensionPixelSize(R.dimen.thumbnail_preview_size)

    init {
        ButterKnife.bind(this, view)
        flProductHolder.setOnClickListener(this)
    }

    companion object {
        fun create(parent: ViewGroup): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            return ProductViewHolder(view)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val product = item as ProductItemViewModel
        with(product) {
            tvName.text = name
            tvDescription.text = tagline
            loadImage(imageUrl)
        }
    }

    private fun loadImage(imageUrl: String) {

        val controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(ResizeImageUrlProvider.overrideUrl(imageUrl, thumbNailSize)))
                .setImageRequest(ImageRequest.fromUri(imageUrl))
                .setOldController(ivProduct.controller)
                .build()
        ivProduct.hierarchy.setProgressBarImage(CircleProgressBarDrawable(itemView.context))
        ivProduct.controller = controller

    }
}