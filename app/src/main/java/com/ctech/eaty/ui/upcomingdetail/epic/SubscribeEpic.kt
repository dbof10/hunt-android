package com.ctech.eaty.ui.upcomingdetail.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.upcomingdetail.action.UpcomingAction
import com.ctech.eaty.ui.upcomingdetail.result.SubscribeResult
import com.ctech.eaty.ui.upcomingdetail.state.UpcomingProductState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class SubscribeEpic(private val productRepository: ProductRepository,
                    private val threadScheduler: ThreadScheduler) : Epic<UpcomingProductState> {

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<UpcomingProductState>): Observable<SubscribeResult> {

        return action.ofType(UpcomingAction.SUBSCRIBE::class.java)
                .flatMap {
                    productRepository.subscribeUpcomingProduct(it.id, it.email)
                            .map {
                                SubscribeResult.success()
                            }
                            .onErrorReturn {
                                SubscribeResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(SubscribeResult.inProgress())
                }
    }

}