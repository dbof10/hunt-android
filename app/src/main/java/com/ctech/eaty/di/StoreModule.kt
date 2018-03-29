package com.ctech.eaty.di

import android.content.Context
import com.ctech.eaty.entity.Comments
import com.ctech.eaty.network.ProductHuntApi
import com.ctech.eaty.network.SoundCloudApi
import com.ctech.eaty.repository.HomePersister
import com.ctech.eaty.repository.ProductDetailPersister
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
import com.ctech.eaty.ui.topicdetail.action.TopicDetailBarCode
import com.ctech.eaty.ui.user.action.UserProductBarCode
import com.ctech.eaty.ui.vote.action.VoteBarCode
import com.ctech.eaty.util.Constants
import com.ctech.eaty.util.NetworkManager
import com.ctech.eaty.util.rx.NetworkSingleTransformer
import com.google.gson.Gson
import com.nytimes.android.external.fs3.SourcePersisterFactory
import com.nytimes.android.external.store3.base.Persister
import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import com.nytimes.android.external.store3.middleware.GsonParserFactory
import dagger.Module
import dagger.Provides
import okio.BufferedSource


@Module
class StoreModule {

    companion object {
        private const val PRODUCT_LIMIT = 10
        private const val COMMENT_LIMIT = 10
        private const val COLLECTION_LIMIT = 10
        private const val TOPIC_LIMIT = 10
        private const val VOTE_LIMIT = 10
        private const val SEARCH_LIMIT = 10
    }

    @Provides
    fun providerRealmPersister(): Persister<ProductResponse, BarCode> {
        return HomePersister()
    }

    @Provides
    fun provideFilePersister(context: Context): Persister<BufferedSource, BarCode> {
        return SourcePersisterFactory.create(context.cacheDir)
    }

    @Provides
    fun provideClearableFilePersister(): Persister<ProductDetailResponse, BarCode> {
        return ProductDetailPersister()
    }

    @Provides
    fun providePersistedHomeStore(apiClient: ProductHuntApi,
                                  persister: Persister<ProductResponse, BarCode>,
                                  networkManager: NetworkManager)
            : Store<ProductResponse, BarCode> {

        return StoreBuilder.parsedWithKey<BarCode, ProductResponse, ProductResponse>()
                .fetcher {
                    apiClient.getPosts(it.key).compose(NetworkSingleTransformer(networkManager))
                }
                .persister(persister)
                .networkBeforeStale()
                .open()
    }

    @Provides
    fun providePersistedSearchStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<ProductResponse, TopicDetailBarCode> {
        return StoreBuilder.parsedWithKey<TopicDetailBarCode, BufferedSource, ProductResponse>()
                .fetcher { barcode -> apiClient.getProductsByTopic(barcode.id, SEARCH_LIMIT, barcode.page).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, ProductResponse::class.java))
                .open()
    }

    @Provides
    fun provideProductDetailStore(apiClient: ProductHuntApi, persister: Persister<ProductDetailResponse, BarCode>)
            : Store<ProductDetailResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, ProductDetailResponse, ProductDetailResponse>()
                .fetcher { barcode -> apiClient.getProductDetail(barcode.key.toInt()) }
                .persister(persister)
                .networkBeforeStale()
                .open()
    }


    @Provides
    fun providePersistedCommentStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<Comments, CommentBarCode> {
        return StoreBuilder.parsedWithKey<CommentBarCode, BufferedSource, Comments>()
                .fetcher { barcode -> apiClient.getComments(barcode.id, COMMENT_LIMIT, barcode.page).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, Comments::class.java))
                .open()
    }

    @Provides
    fun providePersistedCollectionDetailStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<CollectionDetailResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, CollectionDetailResponse>()
                .fetcher { barcode -> apiClient.getCollectionDetail(barcode.key.toInt()).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, CollectionDetailResponse::class.java))
                .open()
    }

    @Provides
    fun providePersistedCollectionStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<CollectionResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, CollectionResponse>()
                .fetcher { barcode -> apiClient.getCollections(COLLECTION_LIMIT, barcode.key.toInt()).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, CollectionResponse::class.java))
                .open()
    }

    @Provides
    fun providePersistedTopicStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<TopicResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, TopicResponse>()
                .fetcher { barcode -> apiClient.getTopics(TOPIC_LIMIT, barcode.key.toInt()).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, TopicResponse::class.java))
                .open()
    }

    @Provides
    fun providePersistedVoteStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<VoteResponse, VoteBarCode> {
        return StoreBuilder.parsedWithKey<VoteBarCode, BufferedSource, VoteResponse>()
                .fetcher { barcode -> apiClient.getVotes(barcode.id, VOTE_LIMIT, barcode.page).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, VoteResponse::class.java))
                .open()
    }

    @Provides
    fun providePersistedRadioStore(apiClient: SoundCloudApi, gson: Gson, persister: Persister<BufferedSource, BarCode>)
            : Store<RadioResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, RadioResponse>()
                .fetcher { barcode -> apiClient.getTracks(barcode.key, Constants.SOUND_CLOUD_ID).map { it.source() } }
                .persister(persister)
                .parser(GsonParserFactory.createSourceParser(gson, RadioResponse::class.java))
                .open()
    }

    @Provides
    fun providePersistedUserStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<UserResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, UserResponse>()
                .fetcher { barcode -> apiClient.getUser(barcode.key.toInt()).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, UserResponse::class.java))
                .open()
    }

    @Provides
    fun providePersistedUserProductStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<ProductResponse, UserProductBarCode> {
        return StoreBuilder.parsedWithKey<UserProductBarCode, BufferedSource, ProductResponse>()
                .fetcher { barcode -> apiClient.getProductsByUser(barcode.id, PRODUCT_LIMIT, barcode.page).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, ProductResponse::class.java))
                .open()
    }

    @Provides
    fun providePersistedNotificationStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<NotificationResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, NotificationResponse>()
                .fetcher { apiClient.getNotifications().map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, NotificationResponse::class.java))
                .open()
    }
}