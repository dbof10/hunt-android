package com.ctech.eaty.ui.topic.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.entity.Topic
import com.ctech.eaty.util.GlideImageLoader
import vn.tiki.noadapter2.AbsViewHolder

class TopicViewHolder(view: View, val imageLoader: GlideImageLoader) : AbsViewHolder(view) {

    @BindView(R.id.ivTopic)
    lateinit var ivCollectionBackground: ImageView

    @BindView(R.id.tvTopicName)
    lateinit var tvCollectionName: TextView


    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
            return TopicViewHolder(view, imageLoader)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val topic = item as Topic
        with(topic) {
            tvCollectionName.text = name
            imageLoader.downloadInto(imageUrl, ivCollectionBackground)
        }


    }
}