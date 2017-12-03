package com.ctech.eaty.ui.settings.viewmodel

import com.ctech.eaty.R
import com.ctech.eaty.base.BasePresenter
import com.ctech.eaty.entity.DataSize
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.ui.settings.view.SettingsView
import com.ctech.eaty.util.FileUtils
import com.ctech.eaty.util.ResourceProvider
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class SettingsViewModel @Inject constructor(private val settingsManager: AppSettingsManager,
                                            private val threadScheduler: ThreadScheduler,
                                            private val resourceProvider: ResourceProvider) : BasePresenter<SettingsView>() {

    val dataSaverEnabled = settingsManager.isDataServerEnabled()

    fun setDataSaver(enabled: Boolean) {
        settingsManager.setDataSaver(enabled)
    }

    fun clearCache() {
        val cacheDir = resourceProvider.getCacheDir()
        Single.create<Long> { e ->
            val length = FileUtils.cleanDirectory(cacheDir)
            e.onSuccess(length)

        }
                .subscribeOn(threadScheduler.workerThread())
                .observeOn(threadScheduler.uiThread())
                .subscribe({
                    view?.showToast(resourceProvider.getString(R.string.cache_cleaned, DataSize.getDataSizeString(it.toDouble())))
                }, Timber::e)

    }


}