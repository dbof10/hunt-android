package com.ctech.eaty.ui.home.epic

import com.ctech.eaty.entity.Product
import com.ctech.eaty.repository.BarcodeGenerator.createHomeNextBarCode
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.response.ProductResponse
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class DataRefreshStrategyComposer(private val productRepository: ProductRepository,
                                  private val dayAgo: Int) : ObservableTransformer<ProductResponse, List<Product>> {

    override fun apply(upstream: Observable<ProductResponse>): Observable<List<Product>> {

        return upstream
                .flatMap {
                    if (it.products.isEmpty()) {
                        return@flatMap productRepository.getHomePosts(createHomeNextBarCode(dayAgo), true)
                    } else {
                        Observable.just(it)
                    }
                }
                .map {
                    it.products
                }

    }

}