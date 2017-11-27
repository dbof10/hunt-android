package com.ctech.eaty.base

import android.app.Activity
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.ctech.eaty.BuildConfig
import com.ctech.eaty.R
import com.ctech.eaty.di.AppInjector
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.soloader.SoLoader
import com.google.firebase.FirebaseApp
import com.google.firebase.perf.FirebasePerformance
import com.squareup.leakcanary.LeakCanary
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import javax.inject.Inject


class EatyApplication : MultiDexApplication(), HasActivityInjector {

    private val MAX_HEAP_SIZE = Runtime.getRuntime().maxMemory().toInt()
    private val MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4
    private val MAX_DISK_CACHE_SIZE = 40L * ByteConstants.MB

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


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

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }
        FirebasePerformance.getInstance().isPerformanceCollectionEnabled = !BuildConfig.DEBUG

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        SoLoader.init(this, false)
        Realm.init(this)
        setupFresco()
        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig(resources.getString(R.string.twitter_consumer_key),
                        resources.getString(R.string.twitter_consumer_secret)))
                .debug(BuildConfig.DEBUG)
                .build()
        Twitter.initialize(config)
    }

    private fun setupFresco() {
        val pipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setBitmapMemoryCacheParamsSupplier {
                    MemoryCacheParams(
                            MAX_MEMORY_CACHE_SIZE,
                            Int.MAX_VALUE,
                            MAX_MEMORY_CACHE_SIZE,
                            Int.MAX_VALUE,
                            Int.MAX_VALUE)
                }
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(this)
                        .setBaseDirectoryPath(cacheDir)
                        .setBaseDirectoryName("fresco_cache")
                        .setMaxCacheSize(MAX_DISK_CACHE_SIZE)
                        .build())
                .setDownsampleEnabled(true)
                .build()
        Fresco.initialize(this, pipelineConfig)

    }


}