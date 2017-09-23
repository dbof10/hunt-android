package com.ctech.eaty.util

import vn.tiki.noadapter2.DiffCallback
import android.support.v7.util.DiffUtil


class DiffInfer(private val diffCallback: DiffCallback) : DiffUtil.Callback() {

    private var items: List<*>? = null
    private var newItems: List<*>? = null

    fun setItems(items: List<*>) {
        this.items = items
    }

    fun setNewItems(newItems: List<*>) {
        this.newItems = newItems
    }

    override fun getOldListSize(): Int {
        return if (items == null) 0 else items!!.size
    }

    override fun getNewListSize(): Int {
        return if (newItems == null) 0 else newItems!!.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)
        if (oldItem == null) {
            return newItem == null
        } else {
            return newItem != null && diffCallback.areItemsTheSame(oldItem, newItem)
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)
        if (oldItem == null) {
            return newItem == null
        } else {
            return diffCallback.areContentsTheSame(oldItem, newItem)
        }
    }

    private fun getNewItem(newItemPosition: Int): Any? {
        return if (newItemPosition >= newItems!!.size) null else newItems!![newItemPosition]
    }

    private fun getOldItem(oldItemPosition: Int): Any? {
        return if (oldItemPosition >= items!!.size) null else items!![oldItemPosition]
    }

}