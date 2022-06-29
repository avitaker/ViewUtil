package com.avinashdavid.viewutil.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avinashdavid.viewutil.BaseAdapterDelegateAdapter
import com.avinashdavid.viewutil.adapters.CheckboxAdapter

class CheckboxFragment: BaseRecyclerViewBottomSheetDialogFragmentWithActions() {
    override fun onPositiveActionClicked() {
        checkboxFragmentViewModel.onCompleted(checkboxFragmentViewModel.finalCheckedItems())
        dismiss()
    }

    override fun onNegativeActionClicked() {
        dismiss()
    }

    override fun createAdapter(): BaseAdapterDelegateAdapter {
        return CheckboxAdapter(requireActivity()) { item: String, selected: Boolean ->
            checkboxFragmentViewModel.checkOrUncheck(item, selected)
        }
    }

    private val checkboxFragmentViewModel : CheckboxFragmentViewModel by viewModels()
    private var onCompleted: (List<Any>) -> Unit = {}
    private var transformer: (Any) -> String = {""}
    private var allItems: List<Any> = listOf()
    private var checkedItems: List<Any> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            checkboxFragmentViewModel.onCompleted = onCompleted
            checkboxFragmentViewModel.transformer = transformer
            checkboxFragmentViewModel.setItems(allItems, checkedItems)
            checkboxFragmentViewModel.pairs.value?.let { pairs ->
                try {
                    recyclerViewAdapter.setItems(pairs)
                } catch (e: Exception) {
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkboxFragmentViewModel.pairs.observe(viewLifecycleOwner) { pairs ->
            pairs?.let {
                recyclerViewAdapter.setItems(pairs)
            }
        }
    }

    companion object {
        fun show(activity: FragmentActivity, allItems: List<Any>, checkedItems: List<Any>, transformer: (Any) -> String, onCompleted: (List<Any>) -> Unit) {
            CheckboxFragment().apply {
                this.allItems = allItems
                this.checkedItems = checkedItems
                this.transformer = transformer
                this.onCompleted = onCompleted
            }.show(activity.supportFragmentManager, "CheckboxFragment")
        }
    }
}

class CheckboxFragmentViewModel : ViewModel() {
    private val _pairs = MutableLiveData<List<Pair<String, Boolean>>>(listOf())
    val pairs : LiveData<List<Pair<String, Boolean>>> get() = _pairs

    private var rawItems = listOf<Any>()

    val checkedStatus = HashMap<String, Boolean>()
    var transformer: (Any) -> String = {""}

    var onCompleted: (List<Any>) -> Unit = {}

    fun setItems(items: List<Any>, checked: List<Any>) {
        rawItems = items
        val p = mutableListOf<Pair<String, Boolean>>()
        items.forEach { item ->
            val transformed = transformer(item)
            val isChecked = checked.firstOrNull { c -> transformer(c) == transformed } != null
            checkedStatus[transformed] = isChecked
            p.add(Pair(transformed, isChecked))
        }
        _pairs.postValue(p)
    }

    fun checkOrUncheck(item: String, selected: Boolean) {
        val p = mutableListOf<Pair<String, Boolean>>()
        checkedStatus[item] = selected
        _pairs.value?.forEach { pair ->
            val transformed = transformer(pair.first)
            p.add(transformed to checkedStatus[transformed]!!)
        }
        _pairs.postValue(p)
    }

    fun finalCheckedItems() : List<Any> {
        val checked = mutableListOf<Any>()
        rawItems.forEach { item ->
            if (checkedStatus[transformer(item)]!!) {
                checked.add(item)
            }
        }
        return checked
    }
}