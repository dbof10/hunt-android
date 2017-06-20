package com.ctech.eaty.repository

import com.ctech.eaty.entity.AccessToken
import com.ctech.eaty.entity.Authentication
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import retrofit2.HttpException

class RefreshTokenFunc(private val apiClient: ProductHuntApi, private val appSettingsManager: AppSettingsManager)
    : Function<Observable<out Throwable>, ObservableSource<AccessToken>> {


    override fun apply(observable: Observable<out Throwable>): ObservableSource<AccessToken> {
        return observable
                .flatMap { throwable ->
                    if (throwable is HttpException) {
                        if (throwable.response().code() == 401) {
                            return@flatMap apiClient.getAccessToken(Authentication.instance)
                                    .doOnNext { accessToken -> appSettingsManager.setToken(accessToken.accessToken) }
                                    .doOnError { appSettingsManager.resetToken() }
                        }
                    }

                    return@flatMap Observable.error<AccessToken>(throwable)
                }
    }


}
