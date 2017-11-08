package com.ctech.eaty.tracking

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseTrackManager @Inject constructor(context: Context) {
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    private val SCREEN_VIEW = "screen_view"
    private val ATTEMPT_LOGIN = "attempt_login"
    private val NOTIFICATION_TYPE = "noti_type"
    private val SEARCH_KEYWORD = "search_keyword"

    fun trackScreenView(screenName: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SOURCE, screenName)
        firebaseAnalytics.logEvent(SCREEN_VIEW, bundle)
    }

    fun trackLoginSuccess(source: String?) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SOURCE, source)
        firebaseAnalytics.logEvent(ATTEMPT_LOGIN, bundle)
    }

    fun trackLoginFail(message: String?) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SOURCE, message)
        firebaseAnalytics.logEvent(ATTEMPT_LOGIN, bundle)
    }

    fun trackNotificationType(type: String?) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SOURCE, type)
        firebaseAnalytics.logEvent(NOTIFICATION_TYPE, bundle)
    }

    fun trackSearchKeyword(keyword: String){
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT, keyword)
        firebaseAnalytics.logEvent(SEARCH_KEYWORD, bundle)
    }
}
