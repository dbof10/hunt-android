package com.ctech.eaty.ui.newletter.di

import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import dagger.Module
import dagger.Provides

@Module
class NewLetterModule {

    @ActivityScope
    @Provides
    fun provideCustomTabHelper(): CustomTabActivityHelper {
        return CustomTabActivityHelper()
    }

}