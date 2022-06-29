package com.avinashdavid.viewutil.adapters

import androidx.fragment.app.FragmentActivity
import com.avinashdavid.viewutil.BaseAdapterDelegateAdapter
import com.avinashdavid.viewutil.delegates.GenericAdapterDelegateWithSimpleTextView
import com.avinashdavid.viewutil.delegates.NullAdapterDelegate

class GenericAdapterWithSimpleTextView<T>(activity: FragmentActivity,
                                          onClick: (T?) -> Any?,
                                          viewTypePredicate: (Any) -> Boolean,
                                          cast: (Any) -> T,
                                          displayString: (T?) -> String?,
                                          changeColorOnClick: Boolean = false,
                                          isItemSelected: (T) -> Boolean? = { null }):
    BaseAdapterDelegateAdapter(activity,
        mutableListOf(
            GenericAdapterDelegateWithSimpleTextView(activity, onClick, viewTypePredicate, cast, displayString, changeColorOnClick, isItemSelected),
            NullAdapterDelegate(activity) { nullPick ->
                onClick(null)
            }))