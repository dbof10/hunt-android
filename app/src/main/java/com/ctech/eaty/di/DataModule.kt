package com.ctech.eaty.di

import android.content.Context
import com.ctech.eaty.entity.Products
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.HomeRepository
import com.ctech.eaty.repository.ProductHuntApi
import com.ctech.eaty.util.rx.ComputationThreadScheduler
import com.ctech.eaty.util.rx.ThreadScheduler
import com.google.gson.Gson
import com.nytimes.android.external.fs2.SourcePersisterFactory
import com.nytimes.android.external.store2.base.Persister
import com.nytimes.android.external.store2.base.impl.BarCode
import com.nytimes.android.external.store2.base.impl.Store
import com.nytimes.android.external.store2.base.impl.StoreBuilder
import com.nytimes.android.external.store2.middleware.GsonParserFactory
import dagger.Module
import dagger.Provides
import okio.BufferedSource
import javax.inject.Singleton


@Module(includes = arrayOf(NetworkModule::class))
class DataModule {

    @Provides
    @Singleton
    fun provideThreadScheduler(): ThreadScheduler {
        return ComputationThreadScheduler()
    }


    @Provides
    @Singleton
    fun homePersister(context: Context): Persister<BufferedSource, BarCode> {
        return SourcePersisterFactory.create(context.cacheDir)
    }

    @Provides
    @Singleton
    fun providePersistedHomeStore(apiClient: ProductHuntApi, gson: Gson, persister: Persister<BufferedSource, BarCode>)
            : Store<Products, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, Products>()
                .fetcher { barcode -> apiClient.getPosts(barcode.key).map { it.source() } }
                .persister(persister)
                .parser(GsonParserFactory.createSourceParser(gson, Products::class.java))
                .open()
    }

    @Provides
    @Singleton
    fun provideHomeRepository(store: Store<Products, BarCode>,
                              apiClient: ProductHuntApi,
                              appSettingsManager: AppSettingsManager): HomeRepository {
        return HomeRepository(store, apiClient, appSettingsManager)
    }


}