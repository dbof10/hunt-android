package com.ctech.eaty.ui.collection.di

import com.ctech.eaty.ui.collection.view.CollectionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class CollectionFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeFragment(): CollectionFragment
}