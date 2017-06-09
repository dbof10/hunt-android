package com.ctech.eaty.util.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ComputationThreadScheduler: ThreadScheduler{
    override fun workerThread(): Scheduler {
        return Schedulers.computation()
    }

    override fun uiThread(): Scheduler {
       return AndroidSchedulers.mainThread()
    }
}