package com.ctech.eaty.repository

import com.ctech.eaty.entity.AccessToken
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.request.OAuthUserRequest
import com.ctech.eaty.response.UserResponse
import com.nytimes.android.external.store2.base.impl.BarCode
import com.nytimes.android.external.store2.base.impl.Store
import io.reactivex.Observable

class UserRepository(private val apiClient: ProductHuntApi,
                     private val userStore: Store<UserResponse, BarCode>,
                     private val appSettings: AppSettingsManager) {

    fun getUserToken(code: String): Observable<AccessToken> = apiClient.getAccessToken(OAuthUserRequest(code = code))

    fun getMe(): Observable<UserDetail> = apiClient.getMe().map {
        it.user
    }

    fun getUser(): Observable<UserDetail> = Observable.just(appSettings.getUser())

    fun getUserById(barcode: BarCode): Observable<UserDetail> = userStore.get(barcode)
            .map {
                it.user
            }
            .retryWhen(RefreshTokenFunc(apiClient, appSettings))

}