package com.ctech.eaty.ui.gallery.viewmodel

import com.ctech.eaty.entity.Media
import com.ctech.eaty.entity.MediaType
import com.ctech.eaty.entity.Platform
import com.ctech.eaty.util.ResizeImageUrlProvider

data class GalleryItemViewModel(private val media: Media) {

    val id: Int get() = media.id
    val type: MediaType get() = media.mediaType
    val imageUrl: String get() {
        return ResizeImageUrlProvider.overrideUrl(media.imageUrl, if (media.width >= 1000) (media.width / 3.5).toInt() else media.width)
    }
    val videoUrl: String? get() {
        if (media.platform == Platform.YOUTUBE) {
            return "youtube://videos/${media.videoId}"
        } else
            return media.videoId
    }
}