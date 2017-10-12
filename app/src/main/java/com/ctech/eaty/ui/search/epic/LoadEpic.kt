package com.ctech.eaty.ui.search.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.SearchRepository
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.search.action.SearchAction
import com.ctech.eaty.ui.search.result.LoadResult
import com.ctech.eaty.ui.search.state.SearchState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(private val searchRepository: SearchRepository,
               private val threadScheduler: ThreadScheduler) : Epic<SearchState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<SearchState>): Observable<LoadResult> {
        return action.ofType(SearchAction.Load::class.java)
                .flatMap {
                    searchRepository.searchPost(0, it.keyword)
                            .map {
                                LoadResult.success(it.map { ProductItemViewModel(it) })
                            }
                            .onErrorReturn {
                                LoadResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadResult.inProgress())
                }
    }
}