package com.ctech.eaty.ui.app.action

import com.ctech.eaty.base.redux.Action
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity

sealed class AppAction: Action()

data class NetworkChangeAction(val connectivity: Connectivity) : AppAction()
