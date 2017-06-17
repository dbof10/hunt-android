package com.ctech.eaty.ui.productdetail.viewmodel

import com.ctech.eaty.entity.ProductDetail
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import io.reactivex.Observable

class ProductDetailViewModel(val stateDispatcher: Observable<ProductDetailState>) {
    fun loading(): Observable<ProductDetailState> {
        return stateDispatcher
                .filter { it.loading }
    }


    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.error != null && !it.loading }
                .map { it.error }
    }


    fun content(): Observable<ProductDetail> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && it.error == null
                }
                .map { it.content }
    }
}