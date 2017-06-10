package com.ctech.eaty.di

import com.ctech.eaty.entity.Comments
import com.ctech.eaty.entity.Products
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.CommentRepository
import com.ctech.eaty.repository.HomeRepository
import com.ctech.eaty.repository.ProductHuntApi
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

}