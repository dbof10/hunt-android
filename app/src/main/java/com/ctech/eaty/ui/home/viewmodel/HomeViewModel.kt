package com.ctech.eaty.ui.home.viewmodel

import com.ctech.eaty.ui.home.state.HomeState
import io.reactivex.Observable

class HomeViewModel(private val stateDispatcher: Observable<HomeState>) {
    fun loading(): Observable<HomeState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun refreshing(): Observable<HomeState> {
        return stateDispatcher
                .filter { it.refreshing }
    }

    fun loadingMore(): Observable<HomeState> {
        return stateDispatcher
                .filter { it.loadingMore }
    }

    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null }
                .map { it.loadError }
    }

    fun refreshError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.refreshError != null }
                .map { it.refreshError }
    }

    fun loadMoreError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadMoreError != null }
                .map(HomeState::loadMoreError)
    }

    fun refreshSuccess(): Observable<HomeState> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && !it.refreshing
                            && !it.loadingMore
                            && it.loadError == null
                            && it.refreshError == null
                            && it.loadMoreError == null
                            && it.dayAgo == 0
                }
    }

    fun content(): Observable<List<HomeItemViewModel>> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && !it.refreshing
                            && !it.loadingMore
                            && it.loadError == null
                            && it.refreshError == null
                            && it.loadMoreError == null
                }
                .map { it.content }
    }
}