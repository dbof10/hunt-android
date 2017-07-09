package com.ctech.eaty.ui.radio.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.entity.TrackStatus
import com.ctech.eaty.ui.radio.viewmodel.TrackItemViewModel
import com.ctech.eaty.util.GlideImageLoader
import vn.tiki.noadapter2.AbsViewHolder


class TrackViewHolder(view: View, private val imageLoader: GlideImageLoader) : AbsViewHolder(view) {

    @BindView(R.id.tvPlay)
    lateinit var tvPlay: TextView

    @BindView(R.id.tvTitle)
    lateinit var tvTitle: TextView

    @BindView(R.id.ivArtwork)
    lateinit var ivArtwork: ImageView

    init {
        ButterKnife.bind(this, view)

    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
            return TrackViewHolder(view, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val track = item as TrackItemViewModel
        with(track) {
            tvTitle.text = title
            tvPlay.visibility = if (status == TrackStatus.PLAYING) View.VISIBLE else View.GONE
            if (imageUrl == null) {
                imageLoader.downloadInto(R.drawable.ic_radio_artwork, ivArtwork)
            } else {
                imageLoader.downloadInto(imageUrl, ivArtwork)
            }
        }


    }
}