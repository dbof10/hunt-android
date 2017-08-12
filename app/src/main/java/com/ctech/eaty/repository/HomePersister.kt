package com.ctech.eaty.repository

import com.ctech.eaty.entity.HomeEntity
import com.ctech.eaty.entity.Product
import com.ctech.eaty.response.ProductResponse
import com.ctech.eaty.util.rx.ThreadScheduler
import com.nytimes.android.external.store3.base.Persister
import com.nytimes.android.external.store3.base.RecordProvider
import com.nytimes.android.external.store3.base.RecordState
import com.nytimes.android.external.store3.base.impl.BarCode
import io.reactivex.Maybe
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmList


class HomePersister(private val realm: Realm, private val threadScheduler: ThreadScheduler) : Persister<ProductResponse, BarCode>, RecordProvider<BarCode> {

    override fun getRecordState(key: BarCode): RecordState {
        val queryResult = realm.where(HomeEntity::class.java)
                .equalTo("key", key.key)
                .findAll()

        if (queryResult.isEmpty()) {
            return RecordState.STALE
        } else {
            return RecordState.FRESH
        }
    }

    override fun write(key: BarCode, raw: ProductResponse): Single<Boolean> {

        val single = Single.create<Boolean> { emitter ->
            realm.executeTransaction {
                val reamList = RealmList<Product>()
                reamList.addAll(raw.products)
                val entity = HomeEntity(key.key, reamList)
                realm.copyToRealmOrUpdate(entity)
                emitter.onSuccess(true)
            }
        }
        return single
                .subscribeOn(threadScheduler.uiThread())
    }

    override fun read(key: BarCode): Maybe<ProductResponse> {
        val maybe = Maybe.create<ProductResponse> { emitter ->
            val queryResult = realm.where(HomeEntity::class.java)
                    .equalTo("key", key.key)
                    .findAll()

            if (queryResult.isEmpty()) {
                emitter.onComplete()
            } else {
                emitter.onSuccess(ProductResponse(queryResult.first().value))
            }
        }
        return maybe.subscribeOn(threadScheduler.uiThread())
    }


}