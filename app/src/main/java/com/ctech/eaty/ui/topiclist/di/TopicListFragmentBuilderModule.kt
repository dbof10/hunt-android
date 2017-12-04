package com.ctech.eaty.ui.topiclist.di

import com.ctech.eaty.ui.topiclist.view.TopicListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class TopicListFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeTopicListFragment(): TopicListFragment

}