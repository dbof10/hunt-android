package com.ctech.eaty.ui.settings.view

import com.ctech.eaty.base.MvpView

interface SettingsView: MvpView{
    fun showToast(message: String)
}