package com.ctech.eaty.ui.login.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.login.action.LoginAction
import com.ctech.eaty.ui.login.result.LoadUserResult
import com.ctech.eaty.ui.login.state.LoginState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadUserEpic(private val userRepository: UserRepository,
                   private val threadScheduler: ThreadScheduler,
                   private val appSettings: AppSettingsManager) : Epic<LoginState> {

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<LoginState>): Observable<LoadUserResult> {
        return action.ofType(LoginAction.LOAD_USER::class.java)
                .flatMap {
                    userRepository.getMe()
                            .doOnNext {
                                appSettings.storeUser(it)
                            }
                            .map {
                                LoadUserResult.success(it)
                            }
                            .onErrorReturn {
                                LoadUserResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadUserResult.inProgress())
                }
    }
}