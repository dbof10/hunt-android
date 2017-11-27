package com.ctech.eaty.util

import android.support.annotation.IdRes
import android.transition.Transition
import android.transition.TransitionSet
import android.view.View
import android.view.ViewGroup

class TransitionUtils private constructor() {

    companion object {


        fun findTransition(set: TransitionSet, clazz: Class<out Transition>): Transition? {
            for (i in 0 until set.transitionCount) {
                val transition = set.getTransitionAt(i)
                if (transition.javaClass == clazz) {
                    return transition
                }
                if (transition is TransitionSet) {
                    val child = findTransition(transition, clazz)
                    if (child != null) return child
                }
            }
            return null
        }

        fun findTransition(set: TransitionSet, clazz: Class<out Transition>, @IdRes targetId: Int): Transition? {
            for (i in 0 until set.transitionCount) {
                val transition = set.getTransitionAt(i)
                if (transition.javaClass == clazz) {
                    if (transition.targetIds.contains(targetId)) {
                        return transition
                    }
                }
                if (transition is TransitionSet) {
                    val child = findTransition(transition, clazz, targetId)
                    if (child != null) return child
                }
            }
            return null
        }

        fun setAncestralClipping(view: View, clipChildren: Boolean): ArrayList<Boolean> {
            return setAncestralClipping(view, clipChildren, ArrayList<Boolean>())
        }

        private fun setAncestralClipping(view: View, clipChildren: Boolean, was: ArrayList<Boolean>): ArrayList<Boolean> {
            if (view is ViewGroup) {
                val group = view
                was.add(group.clipChildren)
                group.clipChildren = clipChildren
            }
            val parent = view.parent
            if (parent != null && parent is ViewGroup) {
                setAncestralClipping(parent, clipChildren, was)
            }
            return was
        }

        fun restoreAncestralClipping(view: View, was: ArrayList<Boolean>) {
            if (view is ViewGroup) {
                val group = view
                group.clipChildren = was.removeAt(0)
            }
            val parent = view.parent
            if (parent != null && parent is ViewGroup) {
                restoreAncestralClipping(parent, was)
            }
        }
    }

    class TransitionListenerAdapter : Transition.TransitionListener {

        override fun onTransitionStart(transition: Transition) {}

        override fun onTransitionEnd(transition: Transition) {}

        override fun onTransitionCancel(transition: Transition) {}

        override fun onTransitionPause(transition: Transition) {}

        override fun onTransitionResume(transition: Transition) {}
    }
}