package com.ctech.eaty.ui.radio.di

import com.ctech.eaty.ui.radio.view.RadioFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class RadioFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeFragment(): RadioFragment
}