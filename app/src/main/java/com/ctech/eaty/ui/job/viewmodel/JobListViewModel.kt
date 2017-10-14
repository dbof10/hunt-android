package com.ctech.eaty.ui.job.viewmodel

import android.support.customtabs.CustomTabsSession
import com.ctech.eaty.ui.job.navigator.JobListNavigation
import com.ctech.eaty.util.rx.Functions
import timber.log.Timber

class JobListViewModel(private val navigator: JobListNavigation) {

    fun navigateJobDetail(url: String, session: CustomTabsSession) {
        navigator.toUrl(url,session)
                .subscribe(Functions.EMPTY, Timber::e)
    }

    fun navigateUser(id: String) {

    }


}