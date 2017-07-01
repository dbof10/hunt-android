package com.ctech.eaty.ui.splash.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ctech.eaty.ui.home.view.HomeActivity

class SplashActivity: Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}