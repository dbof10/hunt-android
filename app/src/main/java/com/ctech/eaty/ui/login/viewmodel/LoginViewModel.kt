package com.ctech.eaty.ui.login.viewmodel

import android.net.Uri
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.login.navigation.LoginNavigation
import com.ctech.eaty.ui.login.state.LoginState
import com.ctech.eaty.util.Constants
import io.reactivex.Completable
import io.reactivex.Observable

class LoginViewModel(private val stateDispatcher: Observable<LoginState>, private val navigation: LoginNavigation) {


    fun loginUrl(): Observable<String> {
        return Observable.just(Uri.Builder()
                .scheme("https")
                .authority("api.producthunt.com")
                .path("v1/oauth/authorize")
                .appendQueryParameter("client_id", Constants.CLIENT_ID)
                .appendQueryParameter("redirect_uri", Constants.REDIRECT_URI)
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("scope", "public private")
                .toString())
    }


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

}