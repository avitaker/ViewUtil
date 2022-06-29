package com.avinashdavid.viewutil.delegates

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.avinashdavid.viewutil.BaseAdapterDelegate
import com.avinashdavid.viewutil.R
import com.avinashdavid.viewutil.viewholders.ViewUtilTextViewViewHolder

class NullAdapterDelegate(activity: FragmentActivity, private val onItemPicked: (PickableNull) -> Unit) : BaseAdapterDelegate(activity) {
    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        return items[position] is PickableNull
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
        val pickableNull = items[position] as PickableNull
        holder.apply {
            tvItem.text = activity.getString(R.string.none)
            tvItem.setOnClickListener {
                onItemPicked(pickableNull)
            }
        }
    }
}

class PickableNull {}

fun List<Any>.addPickableNull(atStart: Boolean = true) : List<Any> {
    val list = this.toMutableList()
    if (atStart) {
        list.add(0, PickableNull())
    } else {
        list.add(PickableNull())
    }
    return list.toList()
}