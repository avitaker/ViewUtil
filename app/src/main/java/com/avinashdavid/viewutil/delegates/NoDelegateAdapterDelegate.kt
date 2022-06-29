package com.avinashdavid.viewutil.delegates

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.avinashdavid.viewutil.BaseAdapterDelegate

class NoDelegateAdapterDelegate(activity: FragmentActivity) : BaseAdapterDelegate(activity) {
    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return BlankViewHolder(ConstraintLayout(activity))
    }

    override fun onBindViewHolder(
        items: List<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder
    ) {
    }

    class BlankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}