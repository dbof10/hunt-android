package com.ctech.eaty.ui.settings.view

import android.os.Bundle
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.ui.settings.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.activity_settings.sDataSaver
import kotlinx.android.synthetic.main.activity_settings.toolbar
import javax.inject.Inject

class SettingsActivity : BaseActivity(), Injectable {

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
        sDataSaver.isChecked = viewModel.dataSaverEnabled
    }

    private fun setupListener(){
        sDataSaver.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setDataSaver(isChecked)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

}