package com.ctech.eaty.ui.user.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.user.action.BarCodeGenerator
import com.ctech.eaty.ui.user.action.UserAction
import com.ctech.eaty.ui.user.result.LoadMoreProductResult
import com.ctech.eaty.ui.user.state.UserDetailState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoreMoreProductEpic(private val userRepository: UserRepository,
                          private val barCodeGenerator: BarCodeGenerator,
                          private val threadScheduler: ThreadScheduler) : Epic<UserDetailState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<UserDetailState>): Observable<LoadMoreProductResult> {
        return action.ofType(UserAction.LoadMoreProduct::class.java)
                .flatMap {
                    val page = state.value.page + 1
                    userRepository.getProduct(barCodeGenerator.generateNextProductBarCode(it.userId, page))
                            .map {
                                LoadMoreProductResult.success(it)
                            }
                            .onErrorReturn {
                                LoadMoreProductResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadMoreProductResult.inProgress())
                }
    }
}