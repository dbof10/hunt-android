package com.ctech.eaty.tracking

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import android.os.Bundle
import javax.inject.Inject

class FirebaseTrackManager @Inject constructor(context: Context) {
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    private val SCREEN_VIEW = "screen_view"

    fun trackScreenView(screenName: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SOURCE, screenName)
        firebaseAnalytics.logEvent(SCREEN_VIEW, bundle)
    }
}
