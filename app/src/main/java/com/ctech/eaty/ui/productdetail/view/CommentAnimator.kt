package com.ctech.eaty.ui.productdetail.view;

import android.support.v7.widget.RecyclerView
import com.ctech.eaty.widget.recyclerview.SlideInItemAnimator

class CommentAnimator : SlideInItemAnimator() {

    private var animateMoves = false


    fun setAnimateMoves(animateMoves: Boolean) {
        this.animateMoves = animateMoves
    }

    override fun animateMove(
            holder: RecyclerView.ViewHolder, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        if (!animateMoves) {
            dispatchMoveFinished(holder)
            return false
        }
        return super.animateMove(holder, fromX, fromY, toX, toY)
    }
}