package com.ctech.eaty.ui.job.di

import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.job.navigator.JobListNavigation
import com.ctech.eaty.ui.job.viewmodel.JobListViewModel
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import dagger.Module
import dagger.Provides

@Module
class JobListModule {

    @ActivityScope
    @Provides
    fun provideCustomTabHelper(): CustomTabActivityHelper {
        return CustomTabActivityHelper()
    }

}