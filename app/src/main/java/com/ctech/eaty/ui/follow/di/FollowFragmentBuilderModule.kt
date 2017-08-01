package com.ctech.eaty.ui.follow.di

import com.ctech.eaty.ui.follow.view.FollowFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FollowFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeFollowFragment(): FollowFragment
}