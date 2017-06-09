package com.ctech.eaty.di

import com.ctech.eaty.ui.home.di.HomeFragmentBuilderModule
import com.ctech.eaty.ui.home.di.HomeModule
import com.ctech.eaty.ui.home.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(HomeModule::class, HomeFragmentBuilderModule::class))
    abstract fun contributeHomeActivity(): HomeActivity

}