package com.avinashdavid.viewutil.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.avinashdavid.viewutil.BaseAdapterDelegateAdapter
import com.avinashdavid.viewutil.adapters.StringPickableAdapter

abstract class UniversalPickerFragment<T: UniversalPickable> : BaseRecyclerViewBottomSheetDialogFragment() {
    private lateinit var options: List<T>

    abstract fun onItemPicked(item: T)

    override fun createAdapter(): BaseAdapterDelegateAdapter = StringPickableAdapter(requireActivity()) { picked ->
        onItemPicked(picked as T)
        dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdapter.setItems(options)
    }

    companion object {
        fun <T: UniversalPickable> UniversalPickerFragment<T>.show(activity: FragmentActivity, options: List<T>) {
            this.options = options
            show(activity.supportFragmentManager, "UniversalPicker")
        }
    }
}

interface UniversalPickable {
    fun universalPickableTitle() : String
}