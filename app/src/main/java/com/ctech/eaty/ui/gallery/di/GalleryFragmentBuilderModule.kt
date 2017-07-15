package com.ctech.eaty.ui.gallery.di

import com.ctech.eaty.ui.gallery.view.GalleryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class GalleryFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeFragment(): GalleryFragment
}