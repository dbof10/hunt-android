package com.ctech.eaty.di

import android.content.Context
import com.ctech.eaty.entity.Comments
import com.ctech.eaty.repository.ProductHuntApi
import com.ctech.eaty.repository.SoundCloudApi
import com.ctech.eaty.response.*
import com.ctech.eaty.ui.comment.action.CommentBarCode
import com.ctech.eaty.ui.search.action.SearchBarCode
import com.ctech.eaty.ui.vote.action.VoteBarCode
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


@Module
class StoreModule {

    val COMMENT_LIMIT = 10
    val COLLECTION_LIMIT = 10
    val TOPIC_LIMIT = 10
    val VOTE_LIMIT = 10
    val SEARCH_LIMIT = 10

    @Provides
    fun homePersister(context: Context): Persister<BufferedSource, BarCode> {
        return SourcePersisterFactory.create(context.cacheDir)
    }

    @Provides
    fun providePersistedHomeStore(apiClient: ProductHuntApi, gson: Gson, persister: Persister<BufferedSource, BarCode>)
            : Store<ProductResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, ProductResponse>()
                .fetcher { barcode -> apiClient.getPosts(barcode.key).map { it.source() } }
                .persister(persister)
                .parser(GsonParserFactory.createSourceParser(gson, ProductResponse::class.java))
                .open()
    }

    @Provides
    fun providePersistedSearchStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<ProductResponse, SearchBarCode> {
        return StoreBuilder.parsedWithKey<SearchBarCode, BufferedSource, ProductResponse>()
                .fetcher { barcode -> apiClient.getProductsByTopic(barcode.id, SEARCH_LIMIT, barcode.page).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, ProductResponse::class.java))
                .open()
    }

    @Provides
    fun provideProductDetailStore(apiClient: ProductHuntApi, gson: Gson, persister: Persister<BufferedSource, BarCode>)
            : Store<ProductDetailResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, ProductDetailResponse>()
                .fetcher { barcode -> apiClient.getProductDetail(barcode.key.toInt()).map { it.source() } }
                .persister(persister)
                .parser(GsonParserFactory.createSourceParser(gson, ProductDetailResponse::class.java))
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
                .fetcher { barcode -> apiClient.getTracks(barcode.key, "2t9loNQH90kzJcsFCODdigxfp325aq4z").map { it.source() } }
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
}