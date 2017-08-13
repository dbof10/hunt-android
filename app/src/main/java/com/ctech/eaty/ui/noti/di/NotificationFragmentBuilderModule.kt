package com.ctech.eaty.ui.noti.di

import com.ctech.eaty.ui.noti.view.NotificationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class NotificationFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeFragment(): NotificationFragment
}