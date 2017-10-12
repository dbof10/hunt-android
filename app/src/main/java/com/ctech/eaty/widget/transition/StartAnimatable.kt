package com.ctech.eaty.widget.transition

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.transition.Transition
import android.transition.TransitionValues
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import com.ctech.eaty.R

class StartAnimatable : Transition {

    private val animatable: Animatable

    constructor(animatable: Animatable) : super() {
        if (animatable !is Drawable) {
            throw IllegalArgumentException("Non-Drawable resource provided.")
        }
        this.animatable = animatable
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.StartAnimatable)
        val drawable = a.getDrawable(R.styleable.StartAnimatable_android_src)
        a.recycle()
        if (drawable is Animatable) {
            animatable = drawable
        } else {
            throw IllegalArgumentException("Non-Animatable resource provided.")
        }
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        // no-op
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        // no-op
    }

    override fun createAnimator(sceneRoot: ViewGroup,
                                startValues: TransitionValues,
                                endValues: TransitionValues?): Animator? {
        if (endValues == null || endValues.view !is ImageView)
            return null

        val iv = endValues.view as ImageView
        iv.setImageDrawable(animatable as Drawable?)

        // need to return a non-null Animator even though we just want to listen for the start
        val transition = ValueAnimator.ofInt(0, 1)
        transition.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                animatable.start()
            }
        })
        return transition
    }
}