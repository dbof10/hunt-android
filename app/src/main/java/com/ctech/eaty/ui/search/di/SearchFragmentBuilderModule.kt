package com.ctech.eaty.ui.search.di

import com.ctech.eaty.ui.search.view.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class SearchFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

}