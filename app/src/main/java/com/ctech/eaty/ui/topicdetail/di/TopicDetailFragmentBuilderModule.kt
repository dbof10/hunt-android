package com.ctech.eaty.ui.topicdetail.di

import com.ctech.eaty.ui.topicdetail.view.TopicDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class TopicDetailFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeTopicListFragment(): TopicDetailFragment

}