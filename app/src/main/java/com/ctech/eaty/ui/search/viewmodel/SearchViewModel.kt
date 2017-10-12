package com.ctech.eaty.ui.search.viewmodel

import android.util.Log
import com.ctech.eaty.base.BasePresenter
import com.ctech.eaty.error.EmptyElementException
import com.ctech.eaty.ui.search.navigation.SearchNavigation
import com.ctech.eaty.ui.search.state.SearchState
import com.ctech.eaty.ui.search.view.SearchView
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class SearchViewModel(private val stateDispatcher: Observable<SearchState>,
                      private val threadScheduler: ThreadScheduler,
                      private val navigation: SearchNavigation) : BasePresenter<SearchView>() {

    fun render(): Disposable {
        return stateDispatcher
                .observeOn(threadScheduler.uiThread())
                .subscribe {
                    if (it.loading) {
                        view?.showLoading()
                    } else {
                        if (it.loadError != null) {
                            if (it.loadError is EmptyElementException) {
                                view?.showEmpty()
                            } else {
                                view?.showLoadError()
                            }
                        } else if (it.loadingMore) {
                            view?.showLoadingMore()
                        } else if (it.loadMoreError != null) {
                            view?.showLoadMoreError()
                        } else {
                            view?.showContent(it.content)
                        }
                    }
                }
    }

    fun toProduct(id: Int) {
        navigation.toProduct(id)
                .subscribe()
    }
}