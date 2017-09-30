package com.ctech.eaty.ui.profile.viewmodel

import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.profile.state.ProfileState
import io.reactivex.Observable

class ProfileViewModel(private val stateDispatcher: Observable<ProfileState>) {
    fun loading(): Observable<ProfileState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun submitError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.submitError != null }
                .map { it.submitError }
    }

    fun emailError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.emailError != null }
                .map { it.emailError }
    }

    fun nameError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.nameError != null }
                .map { it.nameError }
    }


    fun headlineError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.headlineError != null }
                .map { it.headlineError }
    }

    fun content(): Observable<UserDetail> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && it.submitError == null
                            && it.emailError == null
                            && it.nameError == null
                            && it.headlineError == null
                            && it.content != UserDetail.GUEST

                }
                .map { it.content }
    }
}