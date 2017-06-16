package com.ctech.eaty.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.util.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(DataModule::class))
class AppModule {

    @Provides
    @Singleton
    fun provideAppStorage(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideAppSettingsManager(sharedPreferences: SharedPreferences): AppSettingsManager {
        return AppSettingsManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideResourceProvider(context: Context): ResourceProvider{
        return object :ResourceProvider{
            override fun getString(id: Int) = context.getString(id)
        }
    }

}