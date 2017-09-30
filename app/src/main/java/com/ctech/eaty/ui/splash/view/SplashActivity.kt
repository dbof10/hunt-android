package com.ctech.eaty.ui.splash.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.ui.home.view.HomeActivity
import com.ctech.eaty.ui.onboarding.view.OnboardingActivity
import javax.inject.Inject

class SplashActivity : Activity(), Injectable {

    @Inject
    lateinit var appSettingsManager: AppSettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (appSettingsManager.didSeeOnboarding()) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}