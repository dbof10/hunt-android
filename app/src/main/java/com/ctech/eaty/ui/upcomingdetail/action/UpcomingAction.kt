package com.ctech.eaty.ui.upcomingdetail.action

import com.ctech.eaty.base.redux.Action

object UpcomingAction {
    data class LOAD(val slug: String) : Action()
    data class SUBSCRIBE(val id: String, val email: String) : Action()
}