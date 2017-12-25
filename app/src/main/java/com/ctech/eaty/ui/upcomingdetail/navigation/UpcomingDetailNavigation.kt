package com.ctech.eaty.ui.upcomingdetail.navigation

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsSession
import android.support.v4.content.ContextCompat
import com.ctech.eaty.R
import com.ctech.eaty.ui.upcomingdetail.view.UpcomingProductDetailActivity
import com.ctech.eaty.ui.video.view.YoutubeActivity
import com.ctech.eaty.ui.web.WebviewFallback
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import io.reactivex.Completable
import javax.inject.Inject

class UpcomingDetailNavigation @Inject constructor(private val context: UpcomingProductDetailActivity) {


    fun toUrl(url: String, session: CustomTabsSession?): Completable {
        return Completable.fromAction {
            val intentBuilder = CustomTabsIntent.Builder(session)
            intentBuilder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
            intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            CustomTabActivityHelper.openCustomTab(context, intentBuilder.build(), Uri.parse(url), WebviewFallback())
        }
    }

    fun toYoutube(videoUrl: Uri): Completable {
        return Completable.fromAction {
            val intent = YoutubeActivity.newIntent(context, videoUrl)
            context.startActivity(intent)
        }
    }
}