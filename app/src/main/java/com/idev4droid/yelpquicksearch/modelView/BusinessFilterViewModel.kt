package com.idev4droid.yelpquicksearch.modelView

import android.view.View
import androidx.lifecycle.ViewModel
import com.idev4droid.yelpquicksearch.MainActivity.Companion.businessesViewModel
import com.idev4droid.yelpquicksearch.data.BusinessFilterService
import com.idev4droid.yelpquicksearch.model.BusinessFilter
import com.idev4droid.yelpquicksearch.view.BusinessFilterListRecyclerAdapter

interface BusinessFilterViewModelListener {
    fun filterClicked()
}

class BusinessFilterViewModel(private var listener: BusinessFilterViewModelListener) :
    BusinessFilterListRecyclerAdapter.Listener,
    ViewModel() {
    var filters = BusinessFilterService.filters
    var adapter: BusinessFilterListRecyclerAdapter = BusinessFilterListRecyclerAdapter(this, filters)
    var selectedFilter: BusinessFilter? = null
        set(value) {
            adapter.selectedFilter = value
            field = value
        }

    init {
        selectedFilter = BusinessFilterService.filters[0]
    }

    override fun onItemClick(itemView: View, filter: BusinessFilter?) {
        listener.filterClicked()

        selectedFilter = filter

        businessesViewModel.reset()
        fetchBusinessesWithFilter()

        adapter.notifyDataSetChanged()
    }

    private fun fetchBusinessesWithFilter() {
        businessesViewModel.fetchBusinesses(selectedFilter)
    }
}