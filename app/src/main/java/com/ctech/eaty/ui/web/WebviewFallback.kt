package com.ctech.eaty.ui.web

import android.app.Activity
import android.content.Intent
import android.net.Uri

import com.ctech.eaty.ui.web.support.CustomTabActivityHelper

class WebviewFallback : CustomTabActivityHelper.CustomTabFallback {

    override fun openUri(activity: Activity, uri: Uri) {
        val intent = Intent(activity, WebviewActivity::class.java)
        intent.putExtra(WebviewActivity.EXTRA_URL, uri.toString())
        activity.startActivity(intent)
    }
}