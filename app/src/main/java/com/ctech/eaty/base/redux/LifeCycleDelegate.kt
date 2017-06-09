package com.ctech.eaty.base.redux

class LifeCycleDelegate<State>(private val store: Store<State>) {

    fun onStart() {
        store.startBinding()
    }

    fun onStop(finishing: Boolean) {
        if (finishing) {
            store.stopBinding()
        }
    }
}