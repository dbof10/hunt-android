package com.ctech.eaty.ui.collectiondetail.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.collectiondetail.viewmodel.CollectionHeaderItemViewModel
import com.ctech.eaty.util.GlideImageLoader
import vn.tiki.noadapter2.AbsViewHolder

class CollectionHeaderViewHolder(itemView: View, private val imageLoader: GlideImageLoader) : AbsViewHolder(itemView) {

    @BindView(R.id.tvUserName)
    lateinit var tvUserName: TextView

    @BindView(R.id.tvHeadLine)
    lateinit var tvHeadLine: TextView

    @BindView(R.id.ivAvatar)
    lateinit var ivAvatar: ImageView

    @BindView(R.id.tvCollectionDescription)
    lateinit var tvCollectionDescription: TextView

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_collection_detail_header, parent, false)
            return CollectionHeaderViewHolder(view, imageLoader)
        }
    }

    init {
        ButterKnife.bind(this, itemView)
    }


    override fun bind(item: Any?) {
        super.bind(item)
        val header = item as CollectionHeaderItemViewModel
        with(header) {
            tvUserName.text = userName
            tvHeadLine.text = headLine
            tvCollectionDescription.text = description
            imageLoader.downloadInto(imageUrl, ivAvatar)
        }
    }
}