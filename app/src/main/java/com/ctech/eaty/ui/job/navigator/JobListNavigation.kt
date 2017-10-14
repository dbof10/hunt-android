package com.ctech.eaty.ui.job.navigator

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsSession
import android.support.v4.content.ContextCompat
import com.ctech.eaty.R
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.job.view.JobListActivity
import com.ctech.eaty.ui.user.view.UserActivity
import com.ctech.eaty.ui.web.WebviewFallback
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import io.reactivex.Completable
import javax.inject.Inject

class JobListNavigation @Inject constructor(private val context: JobListActivity) {


    fun toUser(user: UserDetail): Completable {
        return Completable.fromAction {
            val intent = UserActivity.newIntent(context, user)
            context.startActivity(intent)
        }
    }

    fun toUrl(url: String, session: CustomTabsSession): Completable {
        return Completable.fromAction {
            val intentBuilder = CustomTabsIntent.Builder(session)
            intentBuilder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
            intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            CustomTabActivityHelper.openCustomTab(context, intentBuilder.build(), Uri.parse(url), WebviewFallback())
        }

    }

}