package com.ctech.eaty.widget;

import android.support.transition.Transition

open class TransitionListenerAdapter : Transition.TransitionListener {

    override fun onTransitionStart(transition: Transition) {}

    override fun onTransitionEnd(transition: Transition) {}

    override fun onTransitionCancel(transition: Transition) {}

    override fun onTransitionPause(transition: Transition) {}

    override fun onTransitionResume(transition: Transition) {}
}