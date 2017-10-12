package com.ctech.eaty.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.ctech.eaty.base.lifecycle.FragmentEvent
import com.uber.autodispose.LifecycleEndedException
import com.uber.autodispose.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.functions.Function

open class BaseFragment: Fragment(), LifecycleScopeProvider<FragmentEvent> {
    private val lifecycleEvents = BehaviorSubject.create<FragmentEvent>()


    override fun lifecycle(): Observable<FragmentEvent> {
        return lifecycleEvents.hide()
    }

    override fun correspondingEvents(): Function<FragmentEvent, FragmentEvent> {
        return CORRESPONDING_EVENTS
    }

    override fun peekLifecycle(): FragmentEvent? {
        return lifecycleEvents.value
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleEvents.onNext(FragmentEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleEvents.onNext(FragmentEvent.CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleEvents.onNext(FragmentEvent.CREATE_VIEW)
    }

    override fun onStart() {
        super.onStart()
        lifecycleEvents.onNext(FragmentEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleEvents.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        lifecycleEvents.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleEvents.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        lifecycleEvents.onNext(FragmentEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    override fun onDestroy() {
        lifecycleEvents.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
    }

    override fun onDetach() {
        lifecycleEvents.onNext(FragmentEvent.DETACH)
        super.onDetach()
    }

    companion object {

        /**
         * This is a function of current event -> target disposal event. That is to say that if event A
         * returns B, then any stream subscribed to during A will autodispose on B. In Android, we make
         * symmetric boundary conditions. Create -> Destroy, Start -> Stop, etc. For anything after
         * Resume we dispose on the next immediate destruction event. Subscribing after Detach is an
         * error.
         */
        private val CORRESPONDING_EVENTS = Function<FragmentEvent, FragmentEvent> { event ->
            when (event) {
                FragmentEvent.ATTACH -> FragmentEvent.DETACH
                FragmentEvent.CREATE -> FragmentEvent.DESTROY
                FragmentEvent.CREATE_VIEW -> FragmentEvent.DESTROY_VIEW
                FragmentEvent.START -> FragmentEvent.STOP
                FragmentEvent.RESUME -> FragmentEvent.PAUSE
                FragmentEvent.PAUSE -> FragmentEvent.STOP
                FragmentEvent.STOP -> FragmentEvent.DESTROY_VIEW
                FragmentEvent.DESTROY_VIEW -> FragmentEvent.DESTROY
                FragmentEvent.DESTROY -> FragmentEvent.DETACH
                else -> throw LifecycleEndedException("Cannot bind to Fragment lifecycle after detach.")
            }
        }
    }
}