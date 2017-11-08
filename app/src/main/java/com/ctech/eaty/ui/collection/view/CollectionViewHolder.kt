package com.ctech.eaty.ui.collection.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.entity.Collection
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.util.ResizeImageUrlProvider
import vn.tiki.noadapter2.AbsViewHolder


class CollectionViewHolder(view: View, val imageLoader: GlideImageLoader) : AbsViewHolder(view) {

    private val IMAGE_WIDTH = 400

    @BindView(R.id.ivBackground)
    lateinit var ivBackground: ImageView

    @BindView(R.id.tvName)
    lateinit var tvName: TextView

    @BindView(R.id.tvTitle)
    lateinit var tvTitle: TextView

    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
            return CollectionViewHolder(view, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val collection = item as Collection
        with(collection) {
            tvName.text = name
            tvTitle.text = title
            imageLoader.downloadInto(ResizeImageUrlProvider.overrideUrl(imageUrl, IMAGE_WIDTH), ivBackground)
        }


    }
}