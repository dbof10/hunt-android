package com.ctech.eaty.ui.profile.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.UserDetail


data class SubmitResult(val loading: Boolean = false,
                        val submitError: Throwable? = null,
                        val emailError: Throwable? = null,
                        val nameError: Throwable? = null,
                        val headlineError: Throwable? = null,
                        val content: UserDetail = UserDetail.GUEST) : Result {
    companion object {
        fun inProgress(): SubmitResult {
            return SubmitResult(true)
        }

        fun success(content: UserDetail): SubmitResult {
            return SubmitResult(content = content)
        }

        fun fail(throwable: Throwable): SubmitResult {
            return SubmitResult(submitError = throwable)
        }

        fun email(throwable: Throwable): SubmitResult {
            return SubmitResult(emailError = throwable)
        }

        fun userName(throwable: Throwable): SubmitResult {
            return SubmitResult(nameError = throwable)
        }

        fun headline(throwable: Throwable): SubmitResult {
            return SubmitResult(headlineError = throwable)
        }
    }
}