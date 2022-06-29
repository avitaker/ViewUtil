package com.avinashdavid.viewutil.adapters

import androidx.fragment.app.FragmentActivity
import com.avinashdavid.viewutil.BaseAdapterDelegateAdapter
import com.avinashdavid.viewutil.delegates.StringPickableAdapterDelegate
import com.avinashdavid.viewutil.fragments.UniversalPickable

class StringPickableAdapter(activity: FragmentActivity, onItemPicked: (UniversalPickable) -> Unit) :
    BaseAdapterDelegateAdapter(activity, mutableListOf(StringPickableAdapterDelegate(activity, onItemPicked)))