package com.ctech.eaty.ui.noti.viewmodel

import com.ctech.eaty.entity.Notification
import com.ctech.eaty.ui.noti.state.NotificationState
import io.reactivex.Observable

class NotificationViewModel(private val stateDispatcher: Observable<NotificationState>) {
    fun loading(): Observable<NotificationState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null && !it.loading }
                .map { it.loadError!! }
    }

    fun empty(): Observable<NotificationState> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && it.loadError == null
                }
    }


    fun content(): Observable<List<Notification>> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && it.loadError == null
                            && it.content.isNotEmpty()

                }
                .map { it.content }
    }
}