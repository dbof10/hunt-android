package com.ctech.eaty.ui.user.di

import com.ctech.eaty.ui.user.view.UserDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class UserDetailFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserDetailFragment

}