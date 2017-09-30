package com.ctech.eaty.ui.profile.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.profile.result.SubmitResult
import com.ctech.eaty.ui.profile.state.ProfileState
import java.lang.IllegalArgumentException

class ProfileReducer : Reducer<ProfileState> {

    override fun apply(state: ProfileState, result: Result): ProfileState {
        when (result) {
            is SubmitResult -> {
                return when {
                    result.loading -> state.copy(loading = true, submitError = null,
                            emailError = null, nameError = null, headlineError = null)
                    result.submitError != null -> state.copy(loading = false,
                            submitError = result.submitError)
                    result.emailError != null -> state.copy(emailError = result.emailError,
                            nameError = null, headlineError = null)
                    result.nameError != null -> state.copy(emailError = null,
                            nameError = result.nameError, headlineError = null)
                    result.headlineError != null -> state.copy(emailError = null,
                            nameError = null, headlineError = result.headlineError)
                    else -> state.copy(loading = false, submitError = null, emailError = null,
                            nameError = null, headlineError = null,
                            content = result.content)
                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}