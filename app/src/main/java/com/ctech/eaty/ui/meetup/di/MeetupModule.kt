package com.ctech.eaty.ui.meetup.di

import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import dagger.Module
import dagger.Provides

@Module
class MeetupModule {

    @ActivityScope
    @Provides
    fun provideCustomTabHelper(): CustomTabActivityHelper {
        return CustomTabActivityHelper()
    }
}