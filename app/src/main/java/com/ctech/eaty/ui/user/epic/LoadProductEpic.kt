package com.ctech.eaty.ui.user.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.repository.createUserProductBarCode
import com.ctech.eaty.ui.user.action.UserAction
import com.ctech.eaty.ui.user.result.LoadProductResult
import com.ctech.eaty.ui.user.state.UserDetailState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadProductEpic(private val userRepository: UserRepository,
                      private val threadScheduler: ThreadScheduler) : Epic<UserDetailState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<UserDetailState>): Observable<LoadProductResult> {
        return action.ofType(UserAction.LoadProduct::class.java)
                .flatMap {
                    userRepository.getProduct(createUserProductBarCode(it.userId, 1))
                            .map {
                                LoadProductResult.success(it)
                            }
                            .onErrorReturn {
                                LoadProductResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadProductResult.inProgress())
                }
    }
}