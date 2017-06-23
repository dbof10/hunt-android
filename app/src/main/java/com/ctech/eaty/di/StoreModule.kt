package com.ctech.eaty.di

import android.content.Context
import com.ctech.eaty.entity.Comments
import com.ctech.eaty.entity.Products
import com.ctech.eaty.repository.ProductHuntApi
import com.ctech.eaty.response.CollectionResponse
import com.ctech.eaty.response.ProductDetailResponse
import com.ctech.eaty.response.TopicResponse
import com.ctech.eaty.response.VoteResponse
import com.ctech.eaty.ui.comment.action.CommentBarCode
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
import javax.inject.Singleton

@Module
class StoreModule {

    val COMMENT_LIMIT = 10
    val COLLECTION_LIMIT = 10
    val TOPIC_LIMIT = 10
    val VOTE_LIMIT = 10

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
    fun provideProductDetailStore(apiClient: ProductHuntApi, gson: Gson, persister: Persister<BufferedSource, BarCode>)
            : Store<ProductDetailResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, ProductDetailResponse>()
                .fetcher { barcode -> apiClient.getProductDetail(barcode.key.toInt()).map { it.source() } }
                .persister(persister)
                .parser(GsonParserFactory.createSourceParser(gson, ProductDetailResponse::class.java))
                .open()
    }


    @Provides
    @Singleton
    fun providePersistedCommentStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<Comments, CommentBarCode> {
        return StoreBuilder.parsedWithKey<CommentBarCode, BufferedSource, Comments>()
                .fetcher { barcode -> apiClient.getComments(barcode.id, COMMENT_LIMIT, barcode.page).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, Comments::class.java))
                .open()
    }

    @Provides
    @Singleton
    fun providePersistedCollectionStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<CollectionResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, CollectionResponse>()
                .fetcher { barcode -> apiClient.getCollections(COLLECTION_LIMIT, barcode.key.toInt()).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, CollectionResponse::class.java))
                .open()
    }

    @Provides
    @Singleton
    fun providePersistedTopicStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<TopicResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, TopicResponse>()
                .fetcher { barcode -> apiClient.getTopics(TOPIC_LIMIT, barcode.key.toInt()).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, TopicResponse::class.java))
                .open()
    }

    @Provides
    @Singleton
    fun providePersistedVoteStore(apiClient: ProductHuntApi, gson: Gson)
            : Store<VoteResponse, VoteBarCode> {
        return StoreBuilder.parsedWithKey<VoteBarCode, BufferedSource, VoteResponse>()
                .fetcher { barcode -> apiClient.getVotes(barcode.id, VOTE_LIMIT, barcode.page).map { it.source() } }
                .parser(GsonParserFactory.createSourceParser(gson, VoteResponse::class.java))
                .open()
    }
}