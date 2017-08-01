package com.ctech.eaty.di

import com.ctech.eaty.linking.UniversalLinkActivity
import com.ctech.eaty.ui.collection.di.CollectionFragmentBuilderModule
import com.ctech.eaty.ui.collection.di.CollectionModule
import com.ctech.eaty.ui.collection.view.CollectionActivity
import com.ctech.eaty.ui.collectiondetail.di.CollectionDetailFragmentBuilderModule
import com.ctech.eaty.ui.collectiondetail.di.CollectionDetailModule
import com.ctech.eaty.ui.collectiondetail.view.CollectionDetailActivity
import com.ctech.eaty.ui.comment.di.CommentFragmentBuilderModule
import com.ctech.eaty.ui.comment.di.CommentModule
import com.ctech.eaty.ui.comment.view.CommentActivity
import com.ctech.eaty.ui.follow.di.FollowFragmentBuilderModule
import com.ctech.eaty.ui.follow.di.FollowModule
import com.ctech.eaty.ui.follow.view.FollowActivity
import com.ctech.eaty.ui.gallery.di.GalleryFragmentBuilderModule
import com.ctech.eaty.ui.gallery.di.GalleryModule
import com.ctech.eaty.ui.gallery.view.GalleryActivity
import com.ctech.eaty.ui.gallery.view.YoutubeActivity
import com.ctech.eaty.ui.home.di.HomeFragmentBuilderModule
import com.ctech.eaty.ui.home.di.HomeModule
import com.ctech.eaty.ui.home.view.HomeActivity
import com.ctech.eaty.ui.login.di.LoginModule
import com.ctech.eaty.ui.login.view.LoginActivity
import com.ctech.eaty.ui.productdetail.di.ProductDetailFragmentBuilderModule
import com.ctech.eaty.ui.productdetail.di.ProductDetailModule
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.ui.radio.di.RadioFragmentBuilderModule
import com.ctech.eaty.ui.radio.di.RadioModule
import com.ctech.eaty.ui.radio.view.RadioActivity
import com.ctech.eaty.ui.search.di.SearchFragmentBuilderModule
import com.ctech.eaty.ui.search.di.SearchModule
import com.ctech.eaty.ui.search.view.SearchActivity
import com.ctech.eaty.ui.topic.di.TopicFragmentBuilderModule
import com.ctech.eaty.ui.topic.di.TopicModule
import com.ctech.eaty.ui.topic.view.TopicActivity
import com.ctech.eaty.ui.user.di.UserDetailFragmentBuilderModule
import com.ctech.eaty.ui.user.di.UserDetailModule
import com.ctech.eaty.ui.user.view.UserActivity
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

    @ContributesAndroidInjector()
    abstract fun contributeDeepLinkActivity(): UniversalLinkActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(RadioModule::class, RadioFragmentBuilderModule::class))
    abstract fun contributeRadioActivity(): RadioActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(GalleryModule::class, GalleryFragmentBuilderModule::class))
    abstract fun contributeGalleryActivity(): GalleryActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(GalleryModule::class))
    abstract fun contributeYoutubeActivity(): YoutubeActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(LoginModule::class))
    abstract fun contributeLoginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(UserDetailModule::class, UserDetailFragmentBuilderModule::class))
    abstract fun contributeUserActivity(): UserActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(FollowModule::class, FollowFragmentBuilderModule::class))
    abstract fun contributeFollowActivity(): FollowActivity
}