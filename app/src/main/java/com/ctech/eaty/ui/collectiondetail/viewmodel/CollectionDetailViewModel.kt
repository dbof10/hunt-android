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
                .filter { it.loading }
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
                .flatMap {
                    if (it == null)
                        Observable.just(CollectionDetail.EMPTY)
                    else
                        Observable.just(it.content)
                }


    }

    fun header(): Observable<String> {
        return content()
                .map {
                    ResizeImageUrlProvider.getNewUrl(it.backgroundImageUrl, IMAGE_BACKGROUND_WIDTH )
                }
    }

    fun body(): Observable<List<CollectionDetailItemViewModel>> {
        return content()
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