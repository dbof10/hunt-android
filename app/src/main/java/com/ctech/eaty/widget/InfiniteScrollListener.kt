package com.ctech.eaty.widget;

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class InfiniteScrollListener(val linearLayoutManager: LinearLayoutManager, val visibleThreshold: Int,
                             val callbacks: Runnable) : RecyclerView.OnScrollListener() {

    private var previousTotal: Int = 0 // The total number of items in the dataset after the last load
    private var loading: Boolean = true // True if we are still waiting for the last set of data to load.


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy);
        val visibleItemCount = recyclerView.childCount;
        val totalItemCount = linearLayoutManager.itemCount
        val firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal || totalItemCount == 0) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        // End has been reached
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            recyclerView.post(callbacks)
            loading = true
        }
    }
}