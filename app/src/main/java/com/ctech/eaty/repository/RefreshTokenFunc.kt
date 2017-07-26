package com.ctech.eaty.repository

import com.ctech.eaty.entity.AccessToken
import com.ctech.eaty.request.OAuthClientRequest
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
                            return@flatMap apiClient.getAccessToken(OAuthClientRequest.instance)
                                    .doOnNext { accessToken -> appSettingsManager.setClientToken(accessToken.accessToken) }
                                    .doOnError { appSettingsManager.resetClientToken() }
                        }
                    }

                    return@flatMap Observable.error<AccessToken>(throwable)
                }
    }


}
