package com.ctech.eaty.ui.profile.di

import com.ctech.eaty.ui.profile.view.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ProfileFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment
}