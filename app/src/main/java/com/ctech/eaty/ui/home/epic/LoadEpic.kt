package com.ctech.eaty.ui.home.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.repository.createHomeNextBarCode
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.result.LoadResult
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(private val productRepository: ProductRepository,
               private val threadScheduler: ThreadScheduler) : Epic<HomeState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<HomeState>): Observable<LoadResult> {
        return action
                .filter {
                    it == HomeAction.LOAD
                }
                .flatMap {
                    productRepository.getHomePosts(createHomeNextBarCode(0))
                            .compose(DataRefreshStrategyComposer(productRepository, 0))
                            .map {
                                LoadResult.success(
                                        it.map { ProductItemViewModel(it) }
                                )
                            }
                            .onErrorReturn {
                                LoadResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadResult.inProgress())
                }
    }
}