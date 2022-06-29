package com.avinashdavid.viewutil.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avinashdavid.viewutil.BaseAdapterDelegateAdapter
import com.avinashdavid.viewutil.R
import com.avinashdavid.viewutil.makeGone
import com.avinashdavid.viewutil.makeVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

abstract class BaseRecyclerViewBottomSheetDialogFragmentWithSearch : BottomSheetDialogFragment(), SearchView.OnQueryTextListener {
    abstract fun createAdapter() : BaseAdapterDelegateAdapter
    open val searchEnabled: Boolean = true
    open val fABEnabled: Boolean = false

    open fun onFABClicked() { }

    protected lateinit var recyclerViewAdapter: BaseAdapterDelegateAdapter
    protected lateinit var recyclerView: RecyclerView
    protected lateinit var searchView: SearchView

    open fun layoutManager() = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    open fun setupView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        searchView = view.findViewById(R.id.search_view)

        recyclerViewAdapter = createAdapter()
        recyclerView.apply {
            layoutManager = layoutManager()
            adapter = recyclerViewAdapter
        }

        if (searchEnabled) {
            searchView.makeVisible()
            searchView.setOnQueryTextListener(this)
        } else {
            searchView.makeGone()
        }
    }

    @LayoutRes
    open val layoutId = R.layout.viewutil_layout_recyclerview_with_search

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutId, container, false)

        setupView(view)

        return view
    }
}

abstract class BaseRecyclerViewBottomSheetDialogFragment: BaseRecyclerViewBottomSheetDialogFragmentWithSearch() {
    override val searchEnabled: Boolean
        get() = false

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}

abstract class BaseRecyclerViewBottomSheetDialogFragmentWithActionsAndSearch: BaseRecyclerViewBottomSheetDialogFragment() {
    override val layoutId: Int
        get() = R.layout.viewutil_layout_recyclerview_with_search_and_action_buttons

    override val searchEnabled: Boolean
        get() = true

    override val fABEnabled: Boolean
        get() = false

    open val editEnabled: Boolean
        get() = true

    open fun positiveButtonText() = ""

    open fun negativeButtonText() = ""

    protected lateinit var btPositiveAction: MaterialButton
    protected lateinit var btNegativeAction: MaterialButton

    override fun setupView(view: View) {
        super.setupView(view)
        btPositiveAction = view.findViewById(R.id.btPositiveAction)
        btNegativeAction = view.findViewById(R.id.btNegativeAction)
        if (!editEnabled) {
            btPositiveAction.makeGone()
        }
        btPositiveAction.apply {
            setOnClickListener {
                onPositiveActionClicked()
                dismiss()
            }
            text = positiveButtonText()
        }
        btNegativeAction.apply {
            setOnClickListener {
                onNegativeActionClicked()
                dismiss()
            }
            text = negativeButtonText()
        }
    }

    abstract fun onPositiveActionClicked()
    abstract fun onNegativeActionClicked()
}

abstract class BaseRecyclerViewBottomSheetDialogFragmentWithActions: BaseRecyclerViewBottomSheetDialogFragment() {
    override val layoutId: Int
        get() = R.layout.viewutil_layout_recyclerview_with_search_and_action_buttons

    override val fABEnabled: Boolean
        get() = false

    open val positiveActionEnabled: Boolean
        get() = true

    open var positiveActionVisible: Boolean = true
        set(value) {
            field = value
            if (value) {
                btPositiveButton.makeVisible()
            } else {
                btPositiveButton.makeGone()
            }
        }

    protected lateinit var btPositiveButton: MaterialButton
    protected lateinit var btNegativeAction: MaterialButton

    open fun positiveButtonText() = ""
    open fun negativeButtonText() = ""

    override fun setupView(view: View) {
        super.setupView(view)

        btPositiveButton = view.findViewById(R.id.btPositiveAction)
        btNegativeAction = view.findViewById(R.id.btNegativeAction)

        if (!positiveActionEnabled) {
            btPositiveButton.makeGone()
        }
        btPositiveButton.apply {
            setOnClickListener {
                onPositiveActionClicked()
                dismiss()
            }
            text = positiveButtonText()
        }
        btPositiveButton.apply {
            setOnClickListener {
                onNegativeActionClicked()
                dismiss()
            }
            text = negativeButtonText()
        }
    }

    abstract fun onPositiveActionClicked()
    abstract fun onNegativeActionClicked()
}