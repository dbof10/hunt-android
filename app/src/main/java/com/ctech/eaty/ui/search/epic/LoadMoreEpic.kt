package com.ctech.eaty.ui.search.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.SearchRepository
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.search.action.SearchAction
import com.ctech.eaty.ui.search.result.LoadMoreResult
import com.ctech.eaty.ui.search.state.SearchState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadMoreEpic(private val searchRepository: SearchRepository,
                   private val threadScheduler: ThreadScheduler) : Epic<SearchState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<SearchState>): Observable<LoadMoreResult> {
        return action.ofType(SearchAction.LoadMore::class.java)
                .filter {
                    !state.value.loadingMore
                }
                .flatMap {
                    val page = state.value.page + 1
                    searchRepository.searchPost(page, it.keyword)
                            .map {
                                LoadMoreResult.success(page, it.map { ProductItemViewModel(it) })
                            }
                            .onErrorReturn {
                                LoadMoreResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadMoreResult.inProgress())
                }
    }
}