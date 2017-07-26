package com.ctech.eaty.widget.transition

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.support.annotation.Keep
import android.transition.TransitionValues
import android.transition.Visibility
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.util.TransitionUtils


class StaggeredDistanceSlide @Keep constructor(context: Context, attrs: AttributeSet) : Visibility(context, attrs) {


    private val PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation"
    private var spread = 1

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.StaggeredDistanceSlide)
        spread = a.getInteger(R.styleable.StaggeredDistanceSlide_spread, spread)
        a.recycle()
    }

    override fun onAppear(sceneRoot: ViewGroup, view: View,
                          startValues: TransitionValues, endValues: TransitionValues): Animator {
        val position = endValues.values[PROPNAME_SCREEN_LOCATION] as IntArray
        return createAnimator(view, (sceneRoot.height + position[1] * spread).toFloat(), 0f)
    }

    override fun onDisappear(sceneRoot: ViewGroup, view: View,
                             startValues: TransitionValues, endValues: TransitionValues): Animator {
        val position = endValues.values[PROPNAME_SCREEN_LOCATION] as IntArray
        return createAnimator(view, 0f, (sceneRoot.height + position[1] * spread).toFloat())
    }

    private fun createAnimator(
            view: View, startTranslationY: Float, endTranslationY: Float): Animator {
        view.translationY = startTranslationY
        val ancestralClipping = TransitionUtils.setAncestralClipping(view, false)
        val transition = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, endTranslationY)
        transition.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                TransitionUtils.restoreAncestralClipping(view, ancestralClipping)
            }
        })
        return transition
    }


}