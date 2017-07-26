package com.ctech.eaty.ui.home.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.result.LoadUserResult
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadUserEpic(private val userRepository: UserRepository,
                   private val threadScheduler: ThreadScheduler) : Epic<HomeState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<HomeState>): Observable<LoadUserResult> {
        return action.filter {
            it == HomeAction.LOAD_USER
        }.flatMap {
            userRepository.getUser()
                    .map {
                        LoadUserResult.success(it)
                    }
        }
                .subscribeOn(threadScheduler.workerThread())
    }

}