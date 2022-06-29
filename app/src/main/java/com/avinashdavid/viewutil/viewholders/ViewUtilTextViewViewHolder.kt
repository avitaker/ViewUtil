package com.avinashdavid.viewutil.viewholders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.avinashdavid.viewutil.R

class ViewUtilTextViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val clTvItem = itemView.findViewById<View>(R.id.clTvItem)
    val tvItem = itemView.findViewById<TextView>(R.id.tvItem)

    companion object {
        fun create(context: Context, parent: ViewGroup) : ViewUtilTextViewViewHolder {
            val layoutId = R.layout.viewutil_item_text_clickable
            return ViewUtilTextViewViewHolder(LayoutInflater.from(context).inflate(layoutId, parent, false))
        }
    }
}