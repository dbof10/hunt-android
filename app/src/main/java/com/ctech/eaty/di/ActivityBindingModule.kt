package com.ctech.eaty.di

import com.ctech.eaty.ui.collection.di.CollectionFragmentBuilderModule
import com.ctech.eaty.ui.collection.di.CollectionModule
import com.ctech.eaty.ui.collection.view.CollectionActivity
import com.ctech.eaty.ui.comment.di.CommentFragmentBuilderModule
import com.ctech.eaty.ui.comment.di.CommentModule
import com.ctech.eaty.ui.comment.view.CommentActivity
import com.ctech.eaty.ui.home.di.HomeFragmentBuilderModule
import com.ctech.eaty.ui.home.di.HomeModule
import com.ctech.eaty.ui.home.view.HomeActivity
import com.ctech.eaty.ui.topic.di.TopicFragmentBuilderModule
import com.ctech.eaty.ui.topic.di.TopicModule
import com.ctech.eaty.ui.topic.view.TopicActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(HomeModule::class, HomeFragmentBuilderModule::class))
    abstract fun contributeHomeActivity(): HomeActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(CommentModule::class, CommentFragmentBuilderModule::class))
    abstract fun contributeCommentActivity(): CommentActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(CollectionModule::class, CollectionFragmentBuilderModule::class))
    abstract fun contributeCollectionActivity(): CollectionActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(TopicModule::class, TopicFragmentBuilderModule::class))
    abstract fun contributeTopicActivity(): TopicActivity
}