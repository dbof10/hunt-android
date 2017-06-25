package com.ctech.eaty.di

import com.ctech.eaty.ui.collection.di.CollectionFragmentBuilderModule
import com.ctech.eaty.ui.collection.di.CollectionModule
import com.ctech.eaty.ui.collection.view.CollectionActivity
import com.ctech.eaty.ui.collectiondetail.di.CollectionDetailFragmentBuilderModule
import com.ctech.eaty.ui.collectiondetail.di.CollectionDetailModule
import com.ctech.eaty.ui.collectiondetail.view.CollectionDetailActivity
import com.ctech.eaty.ui.comment.di.CommentFragmentBuilderModule
import com.ctech.eaty.ui.comment.di.CommentModule
import com.ctech.eaty.ui.comment.view.CommentActivity
import com.ctech.eaty.ui.home.di.HomeFragmentBuilderModule
import com.ctech.eaty.ui.home.di.HomeModule
import com.ctech.eaty.ui.home.view.HomeActivity
import com.ctech.eaty.ui.productdetail.di.ProductDetailFragmentBuilderModule
import com.ctech.eaty.ui.productdetail.di.ProductDetailModule
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.ui.search.di.SearchFragmentBuilderModule
import com.ctech.eaty.ui.search.di.SearchModule
import com.ctech.eaty.ui.search.view.SearchActivity
import com.ctech.eaty.ui.topic.di.TopicFragmentBuilderModule
import com.ctech.eaty.ui.topic.di.TopicModule
import com.ctech.eaty.ui.topic.view.TopicActivity
import com.ctech.eaty.ui.vote.di.VoteFragmentBuilderModule
import com.ctech.eaty.ui.vote.di.VoteModule
import com.ctech.eaty.ui.vote.view.VoteActivity
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
    @ContributesAndroidInjector(modules = arrayOf(CollectionDetailModule::class, CollectionDetailFragmentBuilderModule::class))
    abstract fun contributeCollectionDetailActivity(): CollectionDetailActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(TopicModule::class, TopicFragmentBuilderModule::class))
    abstract fun contributeTopicActivity(): TopicActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(ProductDetailModule::class, ProductDetailFragmentBuilderModule::class))
    abstract fun contributeProductDetailActivity(): ProductDetailActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(VoteModule::class, VoteFragmentBuilderModule::class))
    abstract fun contributeVoteActivity(): VoteActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(SearchModule::class, SearchFragmentBuilderModule::class))
    abstract fun contributeSearchActivity(): SearchActivity
}