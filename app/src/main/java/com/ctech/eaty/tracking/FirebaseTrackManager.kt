package com.ctech.eaty.tracking

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import android.os.Bundle
import android.os.Message
import javax.inject.Inject

class FirebaseTrackManager @Inject constructor(context: Context) {
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    private val SCREEN_VIEW = "screen_view"
    private val ATTEMPT_LOGIN = "attempt_login"

    fun trackScreenView(screenName: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SOURCE, screenName)
        firebaseAnalytics.logEvent(SCREEN_VIEW, bundle)
    }

    fun trackLoginSuccess(){
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SOURCE, "success")
        firebaseAnalytics.logEvent(ATTEMPT_LOGIN, bundle)
    }

    fun trackLoginFail(message: String?){
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SOURCE, message)
        firebaseAnalytics.logEvent(ATTEMPT_LOGIN, bundle)
    }
}
