package com.ctech.eaty.ui.search.viewmodel

import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.search.state.SearchState
import io.reactivex.Observable

class SearchViewModel(private val stateDispatcher: Observable<SearchState>) {
    fun loading(): Observable<SearchState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun loadingMore(): Observable<SearchState> {
        return stateDispatcher
                .filter { it.loadingMore }
    }

    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null }
                .map { it.loadError }
    }

    fun loadMoreError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadMoreError != null }
                .map(SearchState::loadMoreError)
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
                .map {
                    it.content
                }


    }
}