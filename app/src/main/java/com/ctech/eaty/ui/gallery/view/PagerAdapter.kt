package com.ctech.eaty.ui.gallery.view

import android.app.Activity
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.ctech.eaty.R
import com.ctech.eaty.entity.MediaType
import com.ctech.eaty.ui.gallery.viewmodel.GalleryItemViewModel
import com.google.android.youtube.player.YouTubeThumbnailView


class PagerAdapter(private val context: Activity) : PagerAdapter() {

    private var items: List<GalleryItemViewModel> = emptyList()
    private lateinit var image: ImageView
    private lateinit var video: YouTubeThumbnailView
    private lateinit var loadVideo: (YouTubeThumbnailView, String) -> Unit
    private lateinit var loadImage: (ImageView, View, String) -> Unit
    private lateinit var playVideo: (String) -> Unit

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val layout: ViewGroup
        val item = items[position]
        if (item.type == MediaType.IMAGE) {
            layout = inflater.inflate(R.layout.item_gallery_image, collection, false) as ViewGroup
            image = layout.findViewById(R.id.image) as ImageView
            val progressBar = layout.findViewById(R.id.progressBar)
            loadImage(image, progressBar, item.imageUrl)
        } else {
            layout = inflater.inflate(R.layout.item_gallery_video, collection, false) as ViewGroup
            video = layout.findViewById(R.id.ivThumbnail) as YouTubeThumbnailView
            layout.findViewById(R.id.ivPlay)!!.setOnClickListener {
                playVideo(item.videoUrl!!)
            }
            video.setOnClickListener {
                playVideo(item.videoUrl!!)
            }
            loadVideo(video, item.videoUrl!!)
        }
        collection.addView(layout)
        return layout
    }

    fun onPlayClick(block: (String) -> Unit) {
        playVideo = block
    }

    fun onImageLoad(block: (ImageView, View, String) -> Unit) {
        loadImage = block
    }

    fun onThumbnailLoad(block: (YouTubeThumbnailView, String) -> Unit) {
        loadVideo = block
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    fun setItems(items: List<GalleryItemViewModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getCount(): Int = items.size

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

}