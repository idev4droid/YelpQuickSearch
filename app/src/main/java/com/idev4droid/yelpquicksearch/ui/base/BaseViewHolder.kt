package com.idev4droid.yelpquicksearch.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * BaseViewHolder is the base of all view holders. It mostly used to have a common class when using multiple types of view holders in one adapter.
 */
open class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)