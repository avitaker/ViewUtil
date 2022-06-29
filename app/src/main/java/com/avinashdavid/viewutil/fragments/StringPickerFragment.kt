package com.avinashdavid.viewutil.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.avinashdavid.viewutil.BaseAdapterDelegateAdapter
import com.avinashdavid.viewutil.adapters.GenericAdapterWithSimpleTextView

open class StringPickerFragment : BaseRecyclerViewBottomSheetDialogFragment() {
    private lateinit var options: List<String>
    private var onStringPicked: (String?) -> Unit = { _ -> }

    open fun onItemPicked(item: String) {
        onStringPicked(item)
    }

    protected open val dismissOnClicked = true

    override fun createAdapter(): BaseAdapterDelegateAdapter =
        GenericAdapterWithSimpleTextView<String>(requireActivity(),
            onClick = {
                onItemPicked(it as String)
                if (dismissOnClicked) {
                    dismiss()
                }
            },
            viewTypePredicate = { true },
            cast = { it as String },
            displayString = { it })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdapter.setItems(options)
    }

    companion object {
        fun StringPickerFragment.show(activity: FragmentActivity, options: List<String>, onStringPicked: (String?) -> Unit = { _ -> }) {
            this.options = options
            this.onStringPicked = onStringPicked
            show(activity.supportFragmentManager, "StringPickerFragment")
        }

        fun initiate(activity: FragmentActivity, options: List<String>, onStringPicked: (String?) -> Unit = { _ -> }) {
            StringPickerFragment().apply {
                this.options = options
                this.onStringPicked = onStringPicked
            }.show(activity.supportFragmentManager, "StringPickerFragment")
        }
    }
}