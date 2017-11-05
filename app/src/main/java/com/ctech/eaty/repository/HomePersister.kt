package com.ctech.eaty.repository

import com.ctech.eaty.entity.HomeEntity
import com.ctech.eaty.entity.ProductRealm
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


class HomePersister : Persister<ProductResponse, BarCode>, RecordProvider<BarCode> {

    override fun getRecordState(key: BarCode): RecordState {
        val realm = Realm.getDefaultInstance()

        val queryResult = realm.where(HomeEntity::class.java)
                .findAll()

        val empty = queryResult.isEmpty()

        return if (empty) {
            RecordState.MISSING
        } else {
            RecordState.FRESH
        }
    }

    override fun write(key: BarCode, raw: ProductResponse): Single<Boolean> {

        return Single.create { emitter ->
            val realm = Realm.getDefaultInstance()
            realm.executeTransaction {
                val reamList = RealmList<ProductRealm>()
                reamList.addAll(raw.products.map { it.makeRealm() })
                val entity = HomeEntity(key.key, reamList)
                realm.copyToRealmOrUpdate(entity)
                emitter.onSuccess(true)
            }

        }

    }

    override fun read(key: BarCode): Maybe<ProductResponse> {
        return Maybe.create { emitter ->

            val realm = Realm.getDefaultInstance()
            val queryResult = realm.where(HomeEntity::class.java)
                    .equalTo("key", key.key)
                    .findAll()


            if (queryResult.isEmpty()) {
                emitter.onError(RecordNotFoundException("Record for $key not found"))
            } else {
                val shadow = realm.copyFromRealm(queryResult.first()!!.value)
                val result = shadow.map { it.makeProduct() }
                emitter.onSuccess(ProductResponse(result))
            }
        }
    }

}