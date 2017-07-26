package com.ctech.eaty.ui.home.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.UserDetail

data class LoadUserResult(val content: UserDetail = UserDetail.GUEST) : Result {
    companion object {
        fun success(content: UserDetail): LoadUserResult {
            return LoadUserResult(content)
        }

    }
}