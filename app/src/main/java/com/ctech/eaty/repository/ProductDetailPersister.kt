package com.ctech.eaty.repository

import com.ctech.eaty.entity.ProductDetailEntity
import com.ctech.eaty.error.RecordNotFoundException
import com.ctech.eaty.response.ProductDetailResponse
import com.nytimes.android.external.store3.base.Persister
import com.nytimes.android.external.store3.base.impl.BarCode
import io.reactivex.Maybe
import io.reactivex.Single
import io.realm.Realm

class ProductDetailPersister : Persister<ProductDetailResponse, BarCode> {

    override fun write(key: BarCode, raw: ProductDetailResponse): Single<Boolean> {

        return Single.create { emitter ->
            val realm = Realm.getDefaultInstance()
            realm.executeTransaction {
                val product = raw.post.makeRealm()
                val entity = ProductDetailEntity(key.key, product)
                realm.copyToRealmOrUpdate(entity)
                emitter.onSuccess(true)
            }
        }

    }

    override fun read(key: BarCode): Maybe<ProductDetailResponse> {
        return Maybe.create { emitter ->


            val realm = Realm.getDefaultInstance()
            val queryResult = realm.where(ProductDetailEntity::class.java)
                    .equalTo("key", key.key)
                    .findAll()

            if (queryResult.isEmpty()) {
                emitter.onError(RecordNotFoundException("Record for $key not found"))
            } else {

                val shadow = realm.copyFromRealm(queryResult.first().value)
                val result = shadow.makeProductDetail()
                emitter.onSuccess(ProductDetailResponse(result))
            }
        }
    }


}