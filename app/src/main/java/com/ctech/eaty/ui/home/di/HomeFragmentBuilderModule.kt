package com.ctech.eaty.ui.home.di

import com.ctech.eaty.ui.home.view.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class HomeFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

}