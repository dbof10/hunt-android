package com.ctech.eaty.ui.settings.view

import android.os.Bundle
import android.widget.Toast
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.ui.settings.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.activity_settings.sDataSaver
import kotlinx.android.synthetic.main.activity_settings.toolbar
import kotlinx.android.synthetic.main.activity_settings.tvClearCache
import javax.inject.Inject

class SettingsActivity : BaseActivity(), Injectable, SettingsView {

    @Inject
    lateinit var viewModel: SettingsViewModel

    override fun getScreenName() = "Settings"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        bindViewModel()
        setupListener()
    }

    private fun bindViewModel() {
        viewModel.attachView(this)
        sDataSaver.isChecked = viewModel.dataSaverEnabled
    }

    private fun setupListener() {
        sDataSaver.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setDataSaver(isChecked)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
        tvClearCache.setOnClickListener {
            viewModel.clearCache()
        }
    }

    override fun onDestroy() {
        viewModel.detachView()
        super.onDestroy()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}