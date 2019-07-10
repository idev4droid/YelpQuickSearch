package com.idev4droid.yelpquicksearch.ui.view.list

import androidx.recyclerview.widget.DiffUtil
import com.idev4droid.yelpquicksearch.core.data.model.Business

/**
 * BusinessDiffCallback is used to check for difference between business classes
 */
class BusinessDiffCallback : DiffUtil.ItemCallback<Business>() {
    override fun areItemsTheSame(oldItem: Business, newItem: Business): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Business, newItem: Business): Boolean {
        return oldItem == newItem
    }
}