package com.ctech.eaty.ui.topic.di

import com.ctech.eaty.ui.topic.view.TopicFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class TopicFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeFragment(): TopicFragment
}