package com.ctech.eaty.ui.upcomingdetail.di

import com.ctech.eaty.ui.upcomingdetail.view.SuccessMessageFragment
import com.ctech.eaty.ui.upcomingdetail.view.UpcomingDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UpcomingDetailBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): UpcomingDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeMessageFragment(): SuccessMessageFragment

}