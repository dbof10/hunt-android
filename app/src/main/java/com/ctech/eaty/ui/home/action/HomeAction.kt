package com.ctech.eaty.ui.home.action

import android.content.Intent
import com.ctech.eaty.base.redux.Action

object HomeAction : Action() {
    val LOAD: Action = Action()
    val LOAD_MORE: Action = Action()
    val REFRESH: Action = Action()
    val LOAD_USER: Action = Action()
    val USE_MOBILE_DATA: Action = Action()
}

data class CHECK_RESULT(val requestCode: Int, val resultCode: Int, val data: Intent?) : Action()
