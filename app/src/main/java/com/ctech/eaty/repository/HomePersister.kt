package com.ctech.eaty.repository

import android.os.Handler
import android.os.Looper
import com.ctech.eaty.entity.HomeEntity
import com.ctech.eaty.entity.ImageUrl
import com.ctech.eaty.entity.Product
import com.ctech.eaty.error.RecordNotFoundException
import com.ctech.eaty.response.ProductResponse
import com.nytimes.android.external.store3.base.Parser
import com.nytimes.android.external.store3.base.Persister
import com.nytimes.android.external.store3.base.RecordProvider
import com.nytimes.android.external.store3.base.RecordState
import com.nytimes.android.external.store3.base.impl.BarCode
import io.reactivex.Maybe
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmList


class HomePersister(private val realm: Realm) : Persister<ProductResponse, BarCode>, RecordProvider<BarCode> {

    override fun getRecordState(key: BarCode): RecordState {
        val queryResult = realm.where(HomeEntity::class.java)
                .findAll()

        return if (queryResult.isEmpty()) {
            RecordState.MISSING
        } else {
            RecordState.FRESH
        }
    }

    override fun write(key: BarCode, raw: ProductResponse): Single<Boolean> {

        return Single.create { emitter ->
            Handler(Looper.getMainLooper()).post {
                realm.executeTransaction {
                    val reamList = RealmList<Product>()
                    reamList.addAll(raw.products)
                    val entity = HomeEntity(key.key, reamList)
                    realm.copyToRealmOrUpdate(entity)
                    emitter.onSuccess(true)
                }
            }

        }

    }

    override fun read(key: BarCode): Maybe<ProductResponse> {
        return Maybe.create { emitter ->

            Handler(Looper.getMainLooper()).post {

                val queryResult = realm.where(HomeEntity::class.java)
                        .equalTo("key", key.key)
                        .findAll()

                if (queryResult.isEmpty()) {
                    emitter.onError(RecordNotFoundException("Record for $key not found"))
                } else {
                    emitter.onSuccess(ProductResponse(queryResult.first().value))
                }
            }
        }
    }

    companion object {
        fun createParser(): HomeParser {
            return HomeParser()
        }
    }

    class HomeParser : Parser<ProductResponse, ProductResponse> {
        override fun apply(raw: ProductResponse): ProductResponse {
            val parsed = raw.products.map {
                Product(it.id, it.name + "", it.tagline + "", it.commentsCount,
                        it.votesCount,
                        it.discussionUrl,
                        it.redirectUrl,
                        ImageUrl(it.imageUrl.px48 + "",
                                it.imageUrl.px64 + "",
                                it.imageUrl.px300 + "",
                                it.imageUrl.px850 + ""),
                        it.thumbnail)
            }
            return ProductResponse(parsed)
        }

    }


}