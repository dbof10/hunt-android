package com.ctech.eaty.ui.user.viewmodel

import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.user.navigation.UserDetailNavigation
import com.ctech.eaty.ui.user.state.UserDetailState
import io.reactivex.Observable
import timber.log.Timber

class UserDetailViewModel(private val stateDispatcher: Observable<UserDetailState>,
                          private val navigation: UserDetailNavigation) {


    fun loading(): Observable<UserDetailState> = stateDispatcher
            .filter { it.loading && it.content == UserDetail.GUEST }


    fun loadError(): Observable<Throwable> = stateDispatcher
            .filter { it.loadError != null }
            .map {
                it.loadError!!
            }


    private fun content(): Observable<UserDetail> = stateDispatcher
            .filter {
                !it.loading && it.loadError == null
            }
            .map {
                it.content ?: UserDetail.GUEST
            }


    fun header(): Observable<UserDetail> = content()
            .filter {
                it != UserDetail.GUEST
            }

    fun body(): Observable<List<ProductItemViewModel>> = content()
            .filter {
                it != UserDetail.GUEST
            }
            .map {
                it.products.map { ProductItemViewModel(it) }
            }

    fun navigateProduct(id: Int) {
        navigation.toProduct(id)
                .subscribe({}, Timber::e)
    }

}