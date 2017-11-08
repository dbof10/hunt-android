package com.ctech.eaty.ui.login.state

import com.ctech.eaty.entity.UserDetail

data class LoginState(val loading: Boolean = false,
                      val loadError: Throwable? = null,
                      val tokenGrant: Boolean = false,
                      val firstTime: Boolean = false,
                      val content: UserDetail = UserDetail.GUEST,
                      val loginSource: String? = null)