package com.idev4droid.yelpquicksearch.ui.base

import android.view.View
import kotlinx.android.synthetic.main.recycler_view_network_error_item.view.*

class NetworkErrorViewHolder(view: View) : BaseViewHolder(view) {
    fun bind(errorMessage: Int) {
        val context = itemView.context
        itemView.errorTextView.text = context.getText(errorMessage)
    }
}