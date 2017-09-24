package com.ctech.eaty.ui.login.viewmodel

import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.login.navigation.LoginNavigation
import com.ctech.eaty.ui.login.state.LoginState
import io.reactivex.Completable
import io.reactivex.Observable

class LoginViewModel(private val stateDispatcher: Observable<LoginState>, private val navigation: LoginNavigation) {


    fun loading(): Observable<LoginState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null && !it.loading }
                .map { it.loadError!! }
    }

    fun tokenGrant(): Observable<LoginState> {
        return stateDispatcher
                .filter {
                    it.tokenGrant
                }
    }

    fun content(): Completable {
        return stateDispatcher
                .filter {
                    !it.loading
                            && it.loadError == null
                            && it.content != UserDetail.GUEST
                }
                .map {
                    it.content
                }
                .flatMapCompletable {
                    navigation.toHome(it)
                }
    }

    fun loginWithFacebook() {
        navigation.toFacebook().subscribe()
    }

    fun loginWithTwitter() {
        navigation.toTwitter().subscribe()
    }


}