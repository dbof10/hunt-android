package com.ctech.eaty.di

import com.apollographql.apollo.ApolloClient
import com.ctech.eaty.entity.Comments
import com.ctech.eaty.network.AlgoliaApi
import com.ctech.eaty.network.ProductHuntApi
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.CollectionRepository
import com.ctech.eaty.repository.CommentRepository
import com.ctech.eaty.repository.NotificationRepository
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.repository.RadioRepository
import com.ctech.eaty.repository.SearchRepository
import com.ctech.eaty.repository.TopicRepository
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.repository.VoteRepository
import com.ctech.eaty.repository.mapper.ProductMapper
import com.ctech.eaty.response.CollectionDetailResponse
import com.ctech.eaty.response.CollectionResponse
import com.ctech.eaty.response.NotificationResponse
import com.ctech.eaty.response.ProductDetailResponse
import com.ctech.eaty.response.ProductResponse
import com.ctech.eaty.response.RadioResponse
import com.ctech.eaty.response.TopicResponse
import com.ctech.eaty.response.UserResponse
import com.ctech.eaty.response.VoteResponse
import com.ctech.eaty.ui.comment.action.CommentBarCode
import com.ctech.eaty.ui.topiclist.action.SearchBarCode
import com.ctech.eaty.ui.user.action.UserProductBarCode
import com.ctech.eaty.ui.vote.action.VoteBarCode
import com.ctech.eaty.util.rx.ComputationThreadScheduler
import com.ctech.eaty.util.rx.ThreadScheduler
import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [(NetworkModule::class), (StoreModule::class)])
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
                                 apolloClient: ApolloClient,
                                 productMapper: ProductMapper,
                                 appSettingsManager: AppSettingsManager): ProductRepository {
        return ProductRepository(homeStore, productStore, searchStore, apiClient, apolloClient, productMapper, appSettingsManager)
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

    @Provides
    fun provideSearchRepository(apiClient: AlgoliaApi): SearchRepository {
        return SearchRepository(apiClient)
    }
}