package com.ctech.eaty.di

import com.ctech.eaty.entity.Comments
import com.ctech.eaty.repository.*
import com.ctech.eaty.response.*
import com.ctech.eaty.ui.comment.action.CommentBarCode
import com.ctech.eaty.ui.search.action.SearchBarCode
import com.ctech.eaty.ui.user.action.UserProductBarCode
import com.ctech.eaty.ui.vote.action.VoteBarCode
import com.ctech.eaty.util.rx.ComputationThreadScheduler
import com.ctech.eaty.util.rx.ThreadScheduler
import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
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
    fun provideProductRepository(homeStore: Store<ProductResponse, BarCode>,
                                 productStore: Store<ProductDetailResponse, BarCode>,
                                 searchStore: Store<ProductResponse, SearchBarCode>,
                                 apiClient: ProductHuntApi,
                                 appSettingsManager: AppSettingsManager): ProductRepository {
        return ProductRepository(homeStore, productStore, searchStore, apiClient, appSettingsManager)
    }

    @Provides
    fun provideCommentRepository(store: Store<Comments, CommentBarCode>,
                                 apiClient: ProductHuntApi,
                                 appSettingsManager: AppSettingsManager): CommentRepository {
        return CommentRepository(store, apiClient, appSettingsManager)
    }

    @Provides
    fun provideCollectionRepository(store: Store<CollectionResponse, BarCode>,
                                    collectionDetailStore: Store<CollectionDetailResponse, BarCode>,
                                    apiClient: ProductHuntApi,
                                    appSettingsManager: AppSettingsManager): CollectionRepository {
        return CollectionRepository(store, collectionDetailStore, apiClient, appSettingsManager)
    }

    @Provides
    fun provideNotificationRepository(store: Store<NotificationResponse, BarCode>,
                                      apiClient: ProductHuntApi,
                                      appSettingsManager: AppSettingsManager): NotificationRepository {
        return NotificationRepository(store, apiClient, appSettingsManager)
    }


    @Provides
    fun provideTopicRepository(store: Store<TopicResponse, BarCode>,
                               apiClient: ProductHuntApi,
                               appSettingsManager: AppSettingsManager): TopicRepository {
        return TopicRepository(store, apiClient, appSettingsManager)
    }

    @Provides
    fun provideVoteRepository(store: Store<VoteResponse, VoteBarCode>,
                              apiClient: ProductHuntApi,
                              appSettingsManager: AppSettingsManager): VoteRepository {
        return VoteRepository(store, apiClient, appSettingsManager)
    }


    @Provides
    fun provideRadioRepository(store: Store<RadioResponse, BarCode>): RadioRepository {
        return RadioRepository(store)
    }


    @Provides
    fun provideUserRepository(apiClient: ProductHuntApi, userStore: Store<UserResponse, BarCode>,
                              productStore: Store<ProductResponse, UserProductBarCode>,
                              appSettingsManager: AppSettingsManager): UserRepository {
        return UserRepository(apiClient, userStore, productStore, appSettingsManager)
    }

}