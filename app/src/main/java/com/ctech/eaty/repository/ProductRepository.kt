package com.ctech.eaty.repository

import com.ctech.eaty.entity.Product
import com.ctech.eaty.entity.ProductDetail
import com.ctech.eaty.response.ProductDetailResponse
import com.ctech.eaty.response.ProductResponse
import com.ctech.eaty.ui.search.action.SearchBarCode
import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Observable

class ProductRepository(private val homeStore: Store<ProductResponse, BarCode>,
                        private val productStore: Store<ProductDetailResponse, BarCode>,
                        private val searchStore: Store<ProductResponse, SearchBarCode>,
                        private val apiClient: ProductHuntApi,
                        private val appSettingsManager: AppSettingsManager) {
    fun getHomePosts(barcode: BarCode, forcedLoad: Boolean = false): Observable<List<Product>> {
        val stream = if (forcedLoad) homeStore.fetch(barcode) else homeStore.get(barcode)
        return stream
                .map {
                    it.products
                }
                .toObservable()
                .retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }

    fun getProductDetail(barcode: BarCode): Observable<ProductDetail> {
        return productStore.get(barcode)
                .map {
                    it.post
                }
                .toObservable()
                .retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }

    fun getPostsByTopic(barcode: SearchBarCode): Observable<List<Product>> {
        return searchStore.get(barcode)
                .toObservable()
                .map { it.products }
                .retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }
}