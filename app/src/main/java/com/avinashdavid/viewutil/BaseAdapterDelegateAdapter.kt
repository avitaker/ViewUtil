package com.avinashdavid.viewutil

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.avinashdavid.viewutil.delegates.NoDelegateAdapterDelegate

abstract class BaseAdapterDelegateAdapter(protected val activity: FragmentActivity,
                                          protected val adapterDelegates : MutableList<BaseAdapterDelegate>,
                                          val includeBlankDelegate: Boolean = false) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    init {
        setHasStableIds(true)
        if (includeBlankDelegate) {
            adapterDelegates.add(NoDelegateAdapterDelegate(activity))
        }
        this.adapterDelegates.forEach { delegate ->
            val index = this.adapterDelegates.indexOf(delegate)
            //set viewtype constant to position of delegate in list
            this.adapterDelegates[index] = delegate.apply { viewType(index) }
        }
    }

    private val items = mutableListOf<Any>()
    fun items() = items.toList()

    override fun getItemViewType(position: Int): Int {
        adapterDelegates.forEach { delegate ->
            if (delegate.isForViewType(items, position)) {
                return delegate.viewType()
            }
        }

        //see includeBlankDelegate
        throw IllegalArgumentException("No delegate found for item: ${items[position]}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapterDelegates.firstOrNull { delegate -> delegate.viewType() == viewType }?.onCreateViewHolder(parent) ?: throw IllegalArgumentException("No delegate found")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        adapterDelegates.firstOrNull { delegate -> delegate.viewType() == viewType }?.onBindViewHolder(items, position, holder) ?: throw IllegalArgumentException("No delegate found")
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setItems(newItems: List<Any>?) {
        this.items.apply {
            clear()
            addAll(newItems ?: listOf())
            notifyDataSetChanged()
        }
    }

    fun replaceItem(newItem: Any, predicate: (Any) -> Boolean) {
        items.firstOrNull{ i -> predicate(i) }?.let { itemInList ->
            val index = items.indexOf(itemInList)
            items[index] = newItem
            notifyItemChanged(index)
        }
    }

    fun addItems(newItems: List<Any>) {
        this.items.apply {
            val currentCount = count()
            addAll(newItems)
            notifyItemRangeInserted(currentCount - 1, newItems.count())
        }
    }

    fun clearItems() {
        this.items.apply {
            val currentCount = count()
            clear()
            notifyItemRangeRemoved(0, currentCount - 1)
        }
    }
}