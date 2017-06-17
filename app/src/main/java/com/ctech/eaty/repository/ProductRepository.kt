package com.ctech.eaty.repository

import com.ctech.eaty.entity.Product
import com.ctech.eaty.entity.ProductDetail
import com.ctech.eaty.entity.Products
import com.ctech.eaty.response.ProductDetailResponse
import com.nytimes.android.external.store2.base.impl.BarCode
import com.nytimes.android.external.store2.base.impl.Store
import io.reactivex.Observable

class ProductRepository(private val homeStore: Store<Products, BarCode>,
                        private val productStore: Store<ProductDetailResponse, BarCode>,
                        private val apiClient: ProductHuntApi,
                        private val appSettingsManager: AppSettingsManager) {
    fun getHomePosts(barcode: BarCode, forcedLoad: Boolean = false): Observable<List<Product>> {
        val stream = if (forcedLoad) homeStore.fetch(barcode) else homeStore.get(barcode)
        return stream.map {
            it.products
        }.retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }

    fun getProductDetail(barcode: BarCode): Observable<ProductDetail> {
        return productStore.get(barcode).map {
            it.post
        }.retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }
}