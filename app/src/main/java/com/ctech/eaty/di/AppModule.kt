package com.ctech.eaty.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.ui.app.AppReducer
import com.ctech.eaty.ui.app.AppState
import com.ctech.eaty.ui.app.epic.NetworkChangeEpic
import com.ctech.eaty.util.NetworkManager
import com.ctech.eaty.util.ResourceProvider
import com.google.gson.Gson
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
    fun provideAppSettingsManager(sharedPreferences: SharedPreferences, gson: Gson): AppSettingsManager {
        return AppSettingsManager(sharedPreferences, gson)
    }

    @Provides
    @Singleton
    fun provideResourceProvider(context: Context): ResourceProvider {
        return object : ResourceProvider {
            override fun getString(resId: Int, vararg args: Any) = context.getString(resId, *args)

            override fun getCacheDir() = context.cacheDir

            override fun getString(id: Int) = context.getString(id)
        }
    }

    @Singleton
    @Provides
    fun provideAppStore(networkManager: NetworkManager): Store<AppState> {
        return Store<AppState> (AppState(networkManager.connectionType()), AppReducer(), arrayOf(NetworkChangeEpic()))

    }
}