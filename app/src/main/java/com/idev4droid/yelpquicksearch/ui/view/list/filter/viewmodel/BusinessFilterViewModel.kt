package com.idev4droid.yelpquicksearch.ui.view.list.filter.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.idev4droid.yelpquicksearch.core.data.BusinessFilterService
import com.idev4droid.yelpquicksearch.core.data.model.BusinessFilter
import com.idev4droid.yelpquicksearch.ui.view.list.filter.BusinessFilterListRecyclerAdapter
import javax.inject.Inject

class BusinessFilterViewModel @Inject constructor() :
    BusinessFilterListRecyclerAdapter.Listener,
    ViewModel() {

    var selectedFilter: MutableLiveData<BusinessFilter> = MutableLiveData()

    private var filters = BusinessFilterService.filters
    var adapter: BusinessFilterListRecyclerAdapter = BusinessFilterListRecyclerAdapter(this, filters)

    init {
        setSelectedFilter(BusinessFilterService.filters[0])
    }

    private fun setSelectedFilter(filter: BusinessFilter?) {
        adapter.selectedFilter = filter
        selectedFilter.value = filter
    }

    override fun onItemClick(itemView: View, filter: BusinessFilter?) {
        setSelectedFilter(filter)

        adapter.notifyDataSetChanged()
    }

}