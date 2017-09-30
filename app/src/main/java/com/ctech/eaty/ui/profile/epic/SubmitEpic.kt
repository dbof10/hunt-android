package com.ctech.eaty.ui.profile.epic

import android.util.Patterns
import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.error.EmptyEmailException
import com.ctech.eaty.error.EmptyHeadlineException
import com.ctech.eaty.error.EmptyNameException
import com.ctech.eaty.error.InvalidEmailException
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.profile.action.SubmitAction
import com.ctech.eaty.ui.profile.result.SubmitResult
import com.ctech.eaty.ui.profile.state.ProfileState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class SubmitEpic(private val userRepository: UserRepository,
                 private val appSettingsManager: AppSettingsManager,
                 private val threadScheduler: ThreadScheduler) : Epic<ProfileState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<ProfileState>): Observable<SubmitResult> {
        return action
                .ofType(SubmitAction::class.java)
                .flatMap {
                    if (it.email.isNotEmpty()) {
                        if (Patterns.EMAIL_ADDRESS.matcher(it.email).matches()) {
                            if (it.userName.isNotEmpty()) {
                                if (it.headline.isNotEmpty()) {
                                    userRepository.updateUser(it.email, it.userName, it.headline)
                                            .doOnNext {
                                                appSettingsManager.storeUser(it)
                                            }
                                            .map {
                                                SubmitResult.success(it)
                                            }
                                            .onErrorReturn {
                                                SubmitResult.fail(it)
                                            }
                                            .subscribeOn(threadScheduler.workerThread())
                                            .startWith(SubmitResult.inProgress())
                                } else {
                                    Observable.just(SubmitResult.headline(EmptyHeadlineException("Headline empty")))
                                }
                            } else {
                                Observable.just(SubmitResult.userName(EmptyNameException("Username empty")))

                            }
                        } else {
                            Observable.just(SubmitResult.email(InvalidEmailException("Email invalid")))
                        }
                    } else {
                        Observable.just(SubmitResult.email(EmptyEmailException("Email empty")))
                    }

                }
    }

}