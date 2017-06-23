package com.ctech.eaty.di

import android.content.Context
import com.ctech.eaty.BuildConfig
import com.ctech.eaty.repository.AccessTokenInterceptor
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.ProductHuntApi
import com.ctech.eaty.util.Constants
import com.ctech.eaty.util.DateTimeConverter
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.util.NetworkManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton


@Module
class NetworkModule {

    private val DATE_TIME_TYPE = object : TypeToken<DateTime>() {}.type

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .registerTypeAdapter(DATE_TIME_TYPE, DateTimeConverter())
                .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(appSettingsManager: AppSettingsManager): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val accessTokenInterceptor = AccessTokenInterceptor(appSettingsManager)

        builder.addInterceptor(accessTokenInterceptor)


        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Timber.tag("OkHttp").d(message)
            })

            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        return builder
                .followRedirects(false)
                .build()
    }

    @Singleton
    @Provides
    fun provideProductHuntApi(okHttpClient: OkHttpClient): ProductHuntApi {
        return createRestServices(okHttpClient, Constants.API_URL, ProductHuntApi::class.java)
    }

    @Singleton
    @Provides
    fun provideImageLoader(context: Context): GlideImageLoader {
        return GlideImageLoader(context)
    }

    private fun <T> createRestServices(okHttpClient: OkHttpClient, baseUrl: String,
                                       servicesClass: Class<T>): T {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(servicesClass)
    }

    @Singleton
    @Provides
    fun provideNetworkManager(context: Context): NetworkManager {
        return NetworkManager.IMPL(context)
    }
}