package com.ctech.eaty.ui.app.epic

import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.ctech.eaty.annotation.MOBILE
import com.ctech.eaty.annotation.NONE
import com.ctech.eaty.annotation.WIFI
import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.ui.app.AppState
import com.ctech.eaty.ui.app.action.NetworkChangeAction
import com.ctech.eaty.ui.app.result.NetworkChangeResult
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class NetworkChangeEpic : Epic<AppState> {

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<AppState>): Observable<NetworkChangeResult> {
        return action
                .ofType(NetworkChangeAction::class.java)
                .map {
                    if (it.connectivity.state == NetworkInfo.State.DISCONNECTED) {
                        NetworkChangeResult(NONE)
                    } else {
                        if (it.connectivity.type == ConnectivityManager.TYPE_WIFI) {
                            NetworkChangeResult(WIFI)

                        } else {
                            NetworkChangeResult(MOBILE)
                        }
                    }
                }
    }


}