package com.avinashdavid.viewutil.delegates

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.avinashdavid.viewutil.BaseAdapterDelegate
import com.avinashdavid.viewutil.R
import com.avinashdavid.viewutil.viewholders.ViewUtilTextViewViewHolder

class GenericAdapterDelegateWithSimpleTextView<T>(activity: FragmentActivity,
                                                  private val onClick: (T?) -> Any?,
                                                  private val viewTypePredicate: (Any) -> Boolean,
                                                  private val cast: (Any) -> T,
                                                  private val displayString: (T?) -> String?,
                                                  private val changeColorOnClick: Boolean,
                                                  private val itemSelected: (T) -> Boolean?) : BaseAdapterDelegate(activity) {
    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        return viewTypePredicate(items[position]) || items[position] is String
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewUtilTextViewViewHolder(LayoutInflater.from(activity).inflate(R.layout.viewutil_item_text_clickable, parent, false))
    }

    override fun onBindViewHolder(
        items: List<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder
    ) {
        val castObject = try {
            cast(items[position])
        } catch (e: ClassCastException) {
            null
        }
        val tvh = viewHolder as ViewUtilTextViewViewHolder
        tvh.apply {
            tvItem.apply {
                text = displayString(castObject)
                if (castObject?.let(itemSelected) != null) {
                    background = if (itemSelected(castObject) == true) {
                        ColorDrawable(ContextCompat.getColor(activity, R.color.grey_background))
                    } else {
                        ColorDrawable(ContextCompat.getColor(activity, com.google.android.material.R.color.design_default_color_background))
                    }
                }
                setOnClickListener {
                    onClick(castObject)
                    tvh.clTvItem.apply {
                        when {
                            changeColorOnClick -> {
                                val currentBackground = background
                                if (currentBackground is ColorDrawable) {
                                    val currentBackgroundColor = currentBackground.color
                                    val defaultBackgroundColor = if (Build.VERSION.SDK_INT >= 23) {
                                        activity.resources.getColor(com.google.android.material.R.color.design_default_color_background, activity.theme)
                                    } else {
                                        activity.resources.getColor(com.google.android.material.R.color.design_default_color_background)
                                    }

                                    if (currentBackgroundColor == defaultBackgroundColor) {
                                        background = ColorDrawable(ContextCompat.getColor(activity, R.color.grey_background))
                                    } else {
                                        background = ColorDrawable(ContextCompat.getColor(activity, com.google.android.material.R.color.design_default_color_background))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}