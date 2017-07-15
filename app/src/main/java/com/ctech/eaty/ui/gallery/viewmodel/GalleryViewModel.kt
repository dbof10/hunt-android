package com.ctech.eaty.ui.gallery.viewmodel

import android.net.Uri
import com.ctech.eaty.entity.Media
import com.ctech.eaty.ui.gallery.navigation.GalleryNavigation
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class GalleryViewModel(private val navigation: GalleryNavigation) {

    private var media: List<GalleryItemViewModel> = emptyList()
    private val mediaSubject = PublishSubject.create<List<GalleryItemViewModel>>()

    fun setMedia(media: List<Media>) {
        this.media = media.map { GalleryItemViewModel(it) }
        mediaSubject.onNext(this.media)
    }

    fun content(): Observable<List<GalleryItemViewModel>> = mediaSubject

    fun navigateYoutube(url: String) {
        navigation.toYoutube(Uri.parse(url))
                .subscribe({}, Timber::e)
    }

}