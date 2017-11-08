package com.ctech.eaty.ui.noti.state

import com.ctech.eaty.entity.Notification

data class NotificationState(val loading: Boolean = false,
                             val loadError: Throwable? = null,
                             val content: List<Notification>? = null)
