package com.ctech.eaty.repository

import com.ctech.eaty.entity.Collection
import com.ctech.eaty.entity.CollectionDetail
import com.ctech.eaty.response.CollectionDetailResponse
import com.ctech.eaty.response.CollectionResponse
import com.nytimes.android.external.store2.base.impl.BarCode
import com.nytimes.android.external.store2.base.impl.Store
import io.reactivex.Observable

class CollectionRepository(private val store: Store<CollectionResponse, BarCode>,
                           private val collectionDetailStore: Store<CollectionDetailResponse, BarCode>,
                           private val apiClient: ProductHuntApi,
                           private val appSettingsManager: AppSettingsManager) {
    fun getCollections(barcode: BarCode): Observable<List<Collection>> {
        return store.get(barcode).map {
            it.collections
        }.retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }

    fun getCollectionDetail(barcode: BarCode): Observable<CollectionDetail> {
        return collectionDetailStore.get(barcode).map {
            it.collection
        }.retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }
}