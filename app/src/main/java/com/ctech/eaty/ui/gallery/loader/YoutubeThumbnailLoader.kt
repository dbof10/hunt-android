package com.ctech.eaty.ui.gallery.loader

import android.net.Uri
import com.ctech.eaty.R
import com.ctech.eaty.util.Constants
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import javax.inject.Inject


class YoutubeThumbnailLoader @Inject constructor() : YouTubeThumbnailView.OnInitializedListener, YouTubeThumbnailLoader.OnThumbnailLoadedListener {

    private var loader: YouTubeThumbnailLoader? = null

    fun loadThumbnailInto(view: YouTubeThumbnailView, url: String) {
        val videoId = Uri.parse(url).lastPathSegment
        view.initialize(Constants.YOUTUBE_API_KEY, this)
        view.tag = videoId
    }

    override fun onInitializationSuccess(view: YouTubeThumbnailView, loader: YouTubeThumbnailLoader) {
        loader.setOnThumbnailLoadedListener(this)
        this.loader = loader
        val videoId = view.tag as String
        loader.setVideo(videoId)
    }

    override fun onInitializationFailure(view: YouTubeThumbnailView, loader: YouTubeInitializationResult) {
        view.setImageResource(R.drawable.ic_photo_placeholder)
    }

    override fun onThumbnailLoaded(view: YouTubeThumbnailView, videoId: String) {

    }

    override fun onThumbnailError(view: YouTubeThumbnailView, errorReason: YouTubeThumbnailLoader.ErrorReason) {
        view.setImageResource(R.drawable.ic_photo_placeholder)
    }

    fun onDestroy() {
        loader?.release()
    }

}