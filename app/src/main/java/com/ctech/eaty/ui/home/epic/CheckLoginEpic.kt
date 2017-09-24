package com.ctech.eaty.ui.home.epic

import android.app.Activity
import android.util.Log
import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.navigation.HomeNavigation
import com.ctech.eaty.ui.home.result.CheckLoginResult
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.login.navigation.LoginNavigation
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class CheckLoginEpic : Epic<HomeState> {

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<HomeState>): Observable<CheckLoginResult> {
        return action.ofType(HomeAction.CHECK_RESULT::class.java)
                .filter {
                    it.resultCode == Activity.RESULT_OK
                            && it.requestCode == HomeNavigation.LOGIN_REQUEST_CODE
                            && it.data != null
                }
                .map {
                    CheckLoginResult.success(it.data!!.getParcelableExtra(LoginNavigation.USER_KEY))
                }
    }
}