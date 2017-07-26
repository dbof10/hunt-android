package com.ctech.eaty.ui.login.action

import android.net.Uri
import com.ctech.eaty.base.redux.Action

sealed class LoginAction : Action(){
    class LOAD_USER: LoginAction()
    class REQUEST_TOKEN(val url: Uri): LoginAction()
}
