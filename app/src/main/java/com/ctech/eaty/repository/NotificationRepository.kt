package com.ctech.eaty.repository

import com.ctech.eaty.entity.Notification
import com.ctech.eaty.network.ProductHuntApi
import com.ctech.eaty.response.NotificationResponse
import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Observable

class NotificationRepository(private val store: Store<NotificationResponse, BarCode>,
                             private val apiClient: ProductHuntApi,
                             private val appSettingsManager: AppSettingsManager) {
    fun getNotifications(barcode: BarCode): Observable<List<Notification>> {
        return store.get(barcode)
                .map {
                    it.notifications
                }
                .toObservable()
                .retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))

    }

}
