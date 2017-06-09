package com.ctech.eaty.repository

import com.ctech.eaty.entity.Product
import com.ctech.eaty.entity.Products
import com.nytimes.android.external.store2.base.impl.BarCode
import com.nytimes.android.external.store2.base.impl.Store
import io.reactivex.Observable

class HomeRepository(private val store: Store<Products, BarCode>,
                     private val apiClient: ProductHuntApi,
                     private val appSettingsManager: AppSettingsManager) {
    fun getHomePosts(barcode: BarCode, forcedLoad: Boolean = false): Observable<List<Product>> {
        val stream = if (forcedLoad) store.fetch(barcode) else store.get(barcode)
        return stream.map {
            it.products
        }.retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }
}