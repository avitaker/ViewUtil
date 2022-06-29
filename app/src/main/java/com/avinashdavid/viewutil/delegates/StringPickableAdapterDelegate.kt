package com.avinashdavid.viewutil.delegates

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.avinashdavid.viewutil.BaseAdapterDelegate
import com.avinashdavid.viewutil.fragments.UniversalPickable
import com.avinashdavid.viewutil.viewholders.ViewUtilTextViewViewHolder

class StringPickableAdapterDelegate(activity: FragmentActivity, private val onItemPicked: (UniversalPickable) -> Unit) : BaseAdapterDelegate(activity) {
    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        return items[position] is UniversalPickable
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewUtilTextViewViewHolder.create(activity, parent)
    }

    override fun onBindViewHolder(
        items: List<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder
    ) {
        val holder = viewHolder as ViewUtilTextViewViewHolder
        val up = items[position] as UniversalPickable
        holder.apply {
            tvItem.text = up.universalPickableTitle()
            tvItem.setOnClickListener {
                onItemPicked(up)
            }
        }
    }
}