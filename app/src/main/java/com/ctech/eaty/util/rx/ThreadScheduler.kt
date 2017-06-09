package com.ctech.eaty.util.rx

import io.reactivex.Scheduler

interface ThreadScheduler{
    fun workerThread(): Scheduler
    fun uiThread(): Scheduler
}