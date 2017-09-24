package com.ctech.eaty.ui.login.action

import com.ctech.eaty.base.redux.Action

sealed class LoginAction : Action() {
    class LOAD_USER : LoginAction()
    class REQUEST_TOKEN(val loginProvider: String, val oauthToken: String, val authTokenSecret: String = "") : LoginAction()
}
