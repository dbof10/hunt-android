package com.ctech.eaty.ui.user.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.repository.createUserBarCode
import com.ctech.eaty.ui.user.action.UserAction
import com.ctech.eaty.ui.user.result.LoadResult
import com.ctech.eaty.ui.user.state.UserDetailState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(private val userRepository: UserRepository,
               private val threadScheduler: ThreadScheduler) : Epic<UserDetailState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<UserDetailState>): Observable<LoadResult> {
        return action.ofType(UserAction.Load::class.java)
                .flatMap {
                    userRepository.getUserById(createUserBarCode(it.id))
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