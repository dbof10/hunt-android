package com.ctech.eaty.ui.settings.viewmodel

import com.ctech.eaty.repository.AppSettingsManager
import javax.inject.Inject

class SettingsViewModel @Inject constructor(private val settingsManager: AppSettingsManager) {

    val dataSaverEnabled = settingsManager.isDataServerEnabled()

    fun setDataSaver(enabled: Boolean) {
        settingsManager.setDataSaver(enabled)
    }
}