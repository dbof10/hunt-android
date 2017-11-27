package com.ctech.eaty.ui.search.viewmodel

import com.ctech.eaty.error.EmptyElementException
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.search.navigation.SearchNavigation
import com.ctech.eaty.ui.search.state.SearchState
import io.reactivex.Observable

class SearchViewModel(private val stateDispatcher: Observable<SearchState>,
                      private val navigation: SearchNavigation) {

    fun loading(): Observable<SearchState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun loadingMore(): Observable<List<ProductItemViewModel>> {
        return stateDispatcher
                .filter { it.loadingMore }
                .map { it.content }
    }

    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null }
                .map { it.loadError }
    }


    fun loadMoreError(): Observable<List<ProductItemViewModel>> {
        return stateDispatcher
                .filter { it.loadMoreError != null }
                .map { it.content }
    }

    fun empty(): Observable<SearchState> {
        return stateDispatcher
                .filter {
                    it.loadError != null && it.loadError is EmptyElementException
                }
    }

    fun content(): Observable<List<ProductItemViewModel>> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && !it.loadingMore
                            && it.loadError == null
                            && it.loadMoreError == null
                            && it.content.isNotEmpty()
                }
                .map { it.content }
    }


    fun toProduct(id: Int) {
        navigation.toProduct(id)
                .subscribe()
    }
}