package com.ctech.eaty.ui.topiclist.di

import com.ctech.eaty.ui.topiclist.view.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class SearchFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

}