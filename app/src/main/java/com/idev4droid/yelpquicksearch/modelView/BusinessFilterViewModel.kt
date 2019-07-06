package com.idev4droid.yelpquicksearch.modelView

import android.view.View
import androidx.lifecycle.ViewModel
import com.idev4droid.yelpquicksearch.core.data.BusinessFilterService
import com.idev4droid.yelpquicksearch.core.data.BusinessRepository
import com.idev4droid.yelpquicksearch.core.data.model.BusinessFilter
import com.idev4droid.yelpquicksearch.ui.view.list.BusinessFilterListRecyclerAdapter
import javax.inject.Inject

interface BusinessFilterViewModelListener {
    fun filterClicked()
}

class BusinessFilterViewModel(private var listener: BusinessFilterViewModelListener) :
    BusinessFilterListRecyclerAdapter.Listener,
    ViewModel() {

    @Inject
    lateinit var businessRepository: BusinessRepository
    var filters = BusinessFilterService.filters
    var adapter: BusinessFilterListRecyclerAdapter =
        BusinessFilterListRecyclerAdapter(this, filters)
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

        //businessRepository.reset()
        fetchBusinessesWithFilter()

        adapter.notifyDataSetChanged()
    }

    private fun fetchBusinessesWithFilter() {
        businessRepository.fetchBusinesses(selectedFilter)
    }
}