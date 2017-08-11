package com.ctech.eaty.repository

import com.ctech.eaty.entity.Topic
import com.ctech.eaty.response.TopicResponse
import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Observable

class TopicRepository(private val store: Store<TopicResponse, BarCode>,
                      private val apiClient: ProductHuntApi,
                      private val appSettingsManager: AppSettingsManager) {
    fun getTopics(barcode: BarCode): Observable<List<Topic>> {
        return store.get(barcode)
                .map {
                    it.topics
                }
                .toObservable()
                .retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }
}