package com.ctech.eaty.ui.gallery.navigation

import android.net.Uri
import com.ctech.eaty.ui.gallery.view.GalleryActivity
import com.ctech.eaty.ui.video.view.YoutubeActivity
import io.reactivex.Completable
import javax.inject.Inject

class GalleryNavigation @Inject constructor(private val context: GalleryActivity) {

    fun toYoutube(videoUrl: Uri): Completable {
        return Completable.fromAction {
            val intent = YoutubeActivity.newIntent(context, videoUrl)
            context.startActivity(intent)
        }
    }
}