package com.ctech.eaty.ui.upcomingdetail.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.UpcomingProductItemProps
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest
import vn.tiki.noadapter2.AbsViewHolder

class UpcomingItemViewHolder(view: View) : AbsViewHolder(view) {

    @BindView(R.id.ivBackground)
    lateinit var ivBackground: SimpleDraweeView

    @BindView(R.id.ivForeground)
    lateinit var ivForeground: SimpleDraweeView

    @BindView(R.id.tvName)
    lateinit var tvName: TextView

    @BindView(R.id.tvDescription)
    lateinit var tvDescription: TextView


    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(parent: ViewGroup): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_upcoming_product, parent, false)
            return UpcomingItemViewHolder(view)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val product = item as UpcomingProductItemProps
        val context = itemView.context
        val backgroundSize = context.resources.getDimensionPixelSize(R.dimen.upcoming_product_height)
        val foregroundSize = context.resources.getDimensionPixelSize(R.dimen.upcoming_foreground_product_size)
        val thumbnailSize = context.resources.getDimensionPixelSize(R.dimen.thumbnail_preview_size)

        with(product) {
            tvName.text = name
            tvDescription.text = tagline

            val controller = Fresco.newDraweeControllerBuilder()
                    .setLowResImageRequest(ImageRequest.fromUri(
                            ResizeImageUrlProvider.overrideUrl(backgroundUrl, thumbnailSize)))
                    .setImageRequest(ImageRequest.fromUri(ResizeImageUrlProvider.overrideUrl(backgroundUrl, backgroundSize / 2)))
                    .setAutoPlayAnimations(true)
                    .setTapToRetryEnabled(true)
                    .build()

            ivBackground.controller = controller

            val foregroundController = Fresco.newDraweeControllerBuilder()
                    .setUri(ResizeImageUrlProvider.overrideUrl(foregroundUrl, foregroundSize / 2))
                    .build()

            ivForeground.controller = foregroundController
        }
    }
}