package com.avinashdavid.viewutil.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.avinashdavid.viewutil.BaseAdapterDelegate
import com.avinashdavid.viewutil.R
import com.google.android.material.checkbox.MaterialCheckBox

class CheckboxAdapterDelegate(activity: FragmentActivity, private val onItemPicked: (item: String, selected: Boolean) -> Unit) : BaseAdapterDelegate(activity) {
    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        return items[position] is Pair<*, *>
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CheckboxViewHolder(LayoutInflater.from(activity).inflate(R.layout.viewutil_item_checkbox, parent, false))
    }

    override fun onBindViewHolder(
        items: List<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder
    ) {
        val holder = viewHolder as CheckboxViewHolder
        val up = items[position] as Pair<String, Boolean>
        holder.apply {
            cbItem.text = up.first
            cbItem.isChecked = up.second
            cbItem.setOnCheckedChangeListener { _, checked ->
                if (up.second != checked) {
                    onItemPicked(up.first, checked)
                }
            }
        }
    }

    class CheckboxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cbItem = itemView.findViewById<MaterialCheckBox>(R.id.cbItem)
    }
}