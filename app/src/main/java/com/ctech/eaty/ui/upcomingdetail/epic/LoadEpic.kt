package com.ctech.eaty.ui.upcomingdetail.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.upcomingdetail.action.UpcomingAction
import com.ctech.eaty.ui.upcomingdetail.result.LoadResult
import com.ctech.eaty.ui.upcomingdetail.state.UpcomingProductState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(private val productRepository: ProductRepository,
               private val threadScheduler: ThreadScheduler) : Epic<UpcomingProductState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<UpcomingProductState>): Observable<LoadResult> {
        return action.ofType(UpcomingAction.LOAD::class.java)
                .flatMap {
                    productRepository.getUpcomingProductDetail(it.slug)
                            .map {
                                LoadResult.success(it)
                            }
                            .onErrorReturn {
                                LoadResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadResult.inProgress())
                }
    }
}