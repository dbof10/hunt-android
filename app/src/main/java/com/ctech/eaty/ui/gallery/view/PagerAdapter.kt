package com.ctech.eaty.ui.gallery.view

import android.app.Activity
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.entity.MediaType
import com.ctech.eaty.ui.gallery.viewmodel.GalleryItemViewModel
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.ctech.eaty.widget.drawable.CircleProgressBarDrawable
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest
import com.google.android.youtube.player.YouTubeThumbnailView


class PagerAdapter(private val context: Activity) : PagerAdapter() {

    private var items: List<GalleryItemViewModel> = emptyList()
    private lateinit var image: SimpleDraweeView
    private lateinit var video: YouTubeThumbnailView
    private lateinit var loadVideo: (YouTubeThumbnailView, String) -> Unit
    private lateinit var playVideo: (String) -> Unit
    private val thumbNailSize = context.resources.getDimensionPixelSize(R.dimen.thumbnail_preview_size)

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val layout: ViewGroup
        val item = items[position]
        if (item.type == MediaType.IMAGE) {
            image = inflater.inflate(R.layout.item_gallery_image, collection, false) as SimpleDraweeView
            loadImage(image, item.imageUrl)
            collection.addView(image)
            return image
        } else {
            layout = inflater.inflate(R.layout.item_gallery_video, collection, false) as ViewGroup
            video = layout.findViewById(R.id.ivThumbnail)
            layout.findViewById<View>(R.id.ivPlay).setOnClickListener {
                playVideo(item.videoUrl!!)
            }
            video.setOnClickListener {
                playVideo(item.videoUrl!!)
            }
            loadVideo(video, item.videoUrl!!)
            collection.addView(layout)
            return layout

        }
    }

    private fun loadImage(image: SimpleDraweeView, imageUrl: String) {
        val controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(ResizeImageUrlProvider.overrideUrl(imageUrl, thumbNailSize)))
                .setImageRequest(ImageRequest.fromUri(imageUrl))
                .build()
        image.hierarchy.setProgressBarImage(CircleProgressBarDrawable(context))
        image.controller = controller
    }

    fun onPlayClick(block: (String) -> Unit) {
        playVideo = block
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