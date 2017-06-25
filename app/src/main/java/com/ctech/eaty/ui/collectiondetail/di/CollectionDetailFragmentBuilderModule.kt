package com.ctech.eaty.ui.collectiondetail.di

import com.ctech.eaty.ui.collectiondetail.view.CollectionDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class CollectionDetailFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): CollectionDetailFragment

}