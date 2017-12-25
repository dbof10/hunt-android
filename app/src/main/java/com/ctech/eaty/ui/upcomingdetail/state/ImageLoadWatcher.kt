package com.ctech.eaty.ui.upcomingdetail.state

import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.entity.LoadEvent
import java.util.Stack
import javax.inject.Inject

typealias OnAnimReady = () -> Unit

@ActivityScope
class ImageLoadWatcher @Inject constructor() {

    private val stack = Stack<LoadEvent>()
    private val THRESHOLD = 2

    var onAnimReady: OnAnimReady? = null

    operator fun plusAssign(event: LoadEvent) {
        stack.add(event)
        notifyWhenReach()
    }

    private fun notifyWhenReach() {
        val successCount = stack.filter {
            it == LoadEvent.SUCCESS
        }.size

        if (successCount == THRESHOLD) {
            onAnimReady?.invoke()
        }
    }

}