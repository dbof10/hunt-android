package com.ctech.eaty.base

import android.app.Activity
import android.app.Application
import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.ctech.eaty.BuildConfig
import com.ctech.eaty.di.AppInjector
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.google.firebase.FirebaseApp
import com.google.firebase.provider.FirebaseInitProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import javax.inject.Inject

class EatyApplication : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var appDelegate: AppDelegate

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
        JodaTimeAndroid.init(this)
        FirebaseApp.initializeApp(this)
        Fabric.with(this, Crashlytics())
        appDelegate.delegateTracking()
    }

}