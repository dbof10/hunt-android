package com.ctech.eaty.di

import com.ctech.eaty.entity.Comments
import com.ctech.eaty.entity.Products
import com.ctech.eaty.repository.*
import com.ctech.eaty.response.CollectionResponse
import com.ctech.eaty.ui.comment.action.CommentBarCode
import com.ctech.eaty.util.rx.ComputationThreadScheduler
import com.ctech.eaty.util.rx.ThreadScheduler
import com.nytimes.android.external.store2.base.impl.BarCode
import com.nytimes.android.external.store2.base.impl.Store
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = arrayOf(NetworkModule::class, StoreModule::class))
class DataModule {

    @Provides
    @Singleton
    fun provideThreadScheduler(): ThreadScheduler {
        return ComputationThreadScheduler()
    }


    @Provides
    @Singleton
    fun provideHomeRepository(store: Store<Products, BarCode>,
                              apiClient: ProductHuntApi,
                              appSettingsManager: AppSettingsManager): HomeRepository {
        return HomeRepository(store, apiClient, appSettingsManager)
    }

    @Provides
    @Singleton
    fun provideCommentRepository(store: Store<Comments, CommentBarCode>,
                                 apiClient: ProductHuntApi,
                                 appSettingsManager: AppSettingsManager): CommentRepository {
        return CommentRepository(store, apiClient, appSettingsManager)
    }

    @Provides
    @Singleton
    fun provideCollectionRepository(store: Store<CollectionResponse, BarCode>,
                                    apiClient: ProductHuntApi,
                                    appSettingsManager: AppSettingsManager): CollectionRepository {
        return CollectionRepository(store, apiClient, appSettingsManager)
    }

}