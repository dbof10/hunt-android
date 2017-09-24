package com.ctech.eaty.ui.login.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.entity.AccessToken
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.login.action.LoginAction
import com.ctech.eaty.ui.login.result.RequestTokenResult
import com.ctech.eaty.ui.login.state.LoginState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class RequestTokenEpic(private val userRepository: UserRepository,
                       private val threadScheduler: ThreadScheduler,
                       private val appSettings: AppSettingsManager) : Epic<LoginState>{

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<LoginState>): Observable<RequestTokenResult> {
        return action.ofType(LoginAction.REQUEST_TOKEN::class.java)
                .flatMap {
                    userRepository.getUserToken(it.loginProvider, it.oauthToken, it.authTokenSecret)
                            .doOnNext {
                                appSettings.setUserToken(it.accessToken)
                            }
                            .map {
                                RequestTokenResult.success(AccessToken(it.accessToken, it.type), it.firstTime)
                            }
                            .onErrorReturn {
                                RequestTokenResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(RequestTokenResult.inProgress())
                }
    }

}