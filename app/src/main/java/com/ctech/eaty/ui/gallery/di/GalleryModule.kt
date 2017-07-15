package com.ctech.eaty.ui.gallery.di

import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.player.MediaController
import com.ctech.eaty.player.YoutubeMediaController
import com.ctech.eaty.ui.gallery.navigation.GalleryNavigation
import com.ctech.eaty.ui.gallery.viewmodel.GalleryViewModel
import com.google.android.youtube.player.YouTubePlayerView
import dagger.Module
import dagger.Provides


@Module
class GalleryModule {

    @ActivityScope
    @Provides
    fun provideMediaController(): MediaController<YouTubePlayerView> {
        return YoutubeMediaController()

    }

    @Provides
    fun provideGalleryViewModel(navigation: GalleryNavigation): GalleryViewModel {
        return GalleryViewModel(navigation)
    }
}