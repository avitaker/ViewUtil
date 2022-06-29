package com.avinashdavid.viewutil

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapterDelegate(protected val activity: FragmentActivity) {
    private var viewType: Int = Int.MIN_VALUE
    fun viewType() = viewType
    fun viewType(viewType: Int) {
        this.viewType = viewType
    }

    abstract fun isForViewType(items: List<Any>, position: Int) : Boolean
    abstract fun onCreateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
    abstract fun onBindViewHolder(items: List<Any>, position: Int, viewHolder: RecyclerView.ViewHolder)
}