package com.ctech.eaty.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ctech.eaty.base.lifecycle.ActivityEvent
import com.ctech.eaty.base.redux.Store
import com.uber.autodispose.LifecycleEndedException
import com.uber.autodispose.LifecycleScopeProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposeWith
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.subjects.BehaviorSubject

abstract class BaseActivity : AppCompatActivity(), LifecycleScopeProvider<ActivityEvent> {
    private val lifecycleEvents = BehaviorSubject.create<ActivityEvent>()

    abstract fun getScreenName(): String

    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleEvents.hide()
    }

    override fun correspondingEvents(): Function<ActivityEvent, ActivityEvent> {
        return CORRESPONDING_EVENTS
    }

    override fun peekLifecycle(): ActivityEvent {
        return lifecycleEvents.value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleEvents.onNext(ActivityEvent.CREATE)
    }

    override fun onStart() {
        super.onStart()
        lifecycleEvents.onNext(ActivityEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleEvents.onNext(ActivityEvent.RESUME)
    }

    override fun onPause() {
        lifecycleEvents.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleEvents.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleEvents.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
    }

    companion object {

        /**
         * This is a function of current event -> target disposal event. That is to say that if event A
         * returns B, then any stream subscribed to during A will autodispose on B. In Android, we make
         * symmetric boundary conditions. Create -> Destroy, Start -> Stop, etc. For anything after
         * Resume we dispose on the next immediate destruction event. Subscribing after Destroy is an
         * error.
         */
        private val CORRESPONDING_EVENTS = Function<ActivityEvent, ActivityEvent> { activityEvent ->
            when (activityEvent) {
                ActivityEvent.CREATE -> ActivityEvent.DESTROY
                ActivityEvent.START -> ActivityEvent.STOP
                ActivityEvent.RESUME -> ActivityEvent.PAUSE
                ActivityEvent.PAUSE -> ActivityEvent.STOP
                ActivityEvent.STOP -> ActivityEvent.DESTROY
                else -> throw LifecycleEndedException("Cannot bind to Activity lifecycle after destroy.")
            }
        }
    }
}