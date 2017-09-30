package com.ctech.eaty.ui.profile.action

import com.ctech.eaty.base.redux.Action

data class SubmitAction(val email: String, val userName: String, val headline: String) : Action()