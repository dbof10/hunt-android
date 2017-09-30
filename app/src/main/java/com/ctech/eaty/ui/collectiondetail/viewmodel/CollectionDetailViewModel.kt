package com.ctech.eaty.ui.collectiondetail.viewmodel

import com.ctech.eaty.entity.CollectionDetail
import com.ctech.eaty.ui.collectiondetail.state.CollectionDetailState
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.util.ResizeImageUrlProvider
import io.reactivex.Observable

class CollectionDetailViewModel(private val stateDispatcher: Observable<CollectionDetailState>) {

    private val IMAGE_BACKGROUND_WIDTH = 600

    fun loading(): Observable<CollectionDetailState> {
        return stateDispatcher
                .filter { it.loading && it.content == CollectionDetail.EMPTY }
    }


    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null }
                .map { it.loadError }
    }


    fun content(): Observable<CollectionDetail> {
        return stateDispatcher
                .filter {
                    !it.loading && it.loadError == null
                }
                .map {
                    it.content ?: CollectionDetail.EMPTY
                }


    }

    fun header(): Observable<String> {
        return content()
                .filter {
                    it != CollectionDetail.EMPTY
                }
                .map {
                    it.backgroundImageUrl
                }
    }

    fun body(): Observable<List<CollectionDetailItemViewModel>> {
        return content()
                .filter {
                    it != CollectionDetail.EMPTY
                }
                .map {
                    val body = ArrayList<CollectionDetailItemViewModel>()
                    body += mapHeader(it)
                    body += it.products.map {
                        ProductItemViewModel(it)
                    }
                    body
                }
    }

    private fun mapHeader(collection: CollectionDetail): CollectionHeaderItemViewModel = CollectionHeaderItemViewModel(collection)

}