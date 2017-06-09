package com.ctech.eaty.ui.home.navigation

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsSession
import android.support.v4.content.ContextCompat
import com.ctech.eaty.R
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.home.ui.HomeActivity
import com.ctech.eaty.ui.web.WebviewFallback
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import javax.inject.Inject


@ActivityScope
class HomeNavigation @Inject constructor(private val context: HomeActivity) {

    fun toUrl(url: String, session: CustomTabsSession) {
        val intentBuilder = CustomTabsIntent.Builder(session)
        intentBuilder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
        intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
        CustomTabActivityHelper.openCustomTab(context, intentBuilder.build(), Uri.parse(url), WebviewFallback())
    }

}