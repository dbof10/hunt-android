package com.ctech.eaty.util

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.annotation.ColorInt
import android.transition.ChangeBounds
import android.transition.TransitionValues
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.widget.drawable.MorphDrawable
import com.ctech.eaty.widget.transition.GravityArcMotion


class MorphTransform(@ColorInt private val startColor: Int, @ColorInt private val endColor: Int,
                     private val startCornerRadius: Int, private val endCornerRadius: Int) : ChangeBounds() {

    private val DEFAULT_DURATION = 300L

    init {
        duration = DEFAULT_DURATION
        pathMotion = GravityArcMotion()
    }

    companion object {

        private val EXTRA_SHARED_ELEMENT_START_COLOR = "EXTRA_SHARED_ELEMENT_START_COLOR"
        private val EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS = "EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS"

        fun addExtras(intent: Intent, @ColorInt startColor: Int,
                      startCornerRadius: Int) {
            intent.putExtra(EXTRA_SHARED_ELEMENT_START_COLOR, startColor)
            intent.putExtra(EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS, startCornerRadius)
        }

        fun setup(activity: Activity,
                  target: View?,
                  @ColorInt endColor: Int,
                  endCornerRadius: Int) {
            val intent = activity.intent
            if (intent == null || !intent.hasExtra(EXTRA_SHARED_ELEMENT_START_COLOR) || !intent.hasExtra(EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS))
                return

            val startColor = activity.intent.getIntExtra(EXTRA_SHARED_ELEMENT_START_COLOR, Color.TRANSPARENT)
            val startCornerRadius = intent.getIntExtra(EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS, 0)

            val sharedEnter = MorphTransform(startColor, endColor, startCornerRadius, endCornerRadius)
            // Reverse the start/end params for the return transition
            val sharedReturn = MorphTransform(endColor, startColor, endCornerRadius, startCornerRadius)
            if (target != null) {
                sharedEnter.addTarget(target)
                sharedReturn.addTarget(target)
            }
            activity.window.sharedElementEnterTransition = sharedEnter
            activity.window.sharedElementReturnTransition = sharedReturn
        }
    }

    override fun createAnimator(sceneRoot: ViewGroup,
                                startValues: TransitionValues,
                                endValues: TransitionValues): Animator? {
        val changeBounds = super.createAnimator(sceneRoot, startValues, endValues) ?: return null

        var interpolator: TimeInterpolator? = interpolator
        if (interpolator == null) {
            interpolator = AnimUtils.getFastOutSlowInInterpolator(sceneRoot.context)
        }

        val background = MorphDrawable(startColor, startCornerRadius.toFloat())
        endValues.view.background = background

        val color = ObjectAnimator.ofArgb(background, MorphDrawable.COLOR, endColor)
        val corners = ObjectAnimator.ofFloat(background, MorphDrawable.CORNER_RADIUS, endCornerRadius.toFloat())

        // ease in the dialog's child views (fade in & staggered slide up)
        if (endValues.view is ViewGroup) {
            val vg = endValues.view as ViewGroup
            val duration = duration / 2
            var offset = (vg.height / 3).toFloat()
            for (i in 0..vg.childCount - 1) {
                val v = vg.getChildAt(i)
                v.translationY = offset
                v.alpha = 0f
                v.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(duration)
                        .setStartDelay(duration).interpolator = interpolator
                offset *= 1.8f
            }
        }

        val transition = AnimatorSet()
        transition.playTogether(changeBounds, corners, color)
        transition.duration = duration
        transition.interpolator = interpolator
        return transition
    }
}