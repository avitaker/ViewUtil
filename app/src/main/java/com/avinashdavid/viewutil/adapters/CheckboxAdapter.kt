package com.avinashdavid.viewutil.adapters

import androidx.fragment.app.FragmentActivity
import com.avinashdavid.viewutil.BaseAdapterDelegateAdapter
import com.avinashdavid.viewutil.delegates.CheckboxAdapterDelegate

class CheckboxAdapter(activity: FragmentActivity, onItemPicked: (item: String, selected: Boolean) -> Unit) :
    BaseAdapterDelegateAdapter(activity, mutableListOf(CheckboxAdapterDelegate(activity, onItemPicked)))