package com.idev4droid.yelpquicksearch.modelView

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.core.data.BusinessRepository
import com.idev4droid.yelpquicksearch.core.data.model.Business
import com.idev4droid.yelpquicksearch.view.BusinessDetailsFragment
import com.idev4droid.yelpquicksearch.view.BusinessListRecyclerAdapter
import java.util.Observable
import java.util.Observer

interface BusinessListViewModelListener {
    fun startLoading()
    fun stopLoading()
    fun useDataLayout()
    fun useNetworkErrorLayout()
}

class BusinessListViewModel(var listener: BusinessListViewModelListener, var businessRepository: BusinessRepository) :
    BusinessListRecyclerAdapter.Listener,
    Observer, BusinessFilterViewModelListener, ViewModel() {

    var businessFilterViewModel = BusinessFilterViewModel(this)
    var adapter: BusinessListRecyclerAdapter = BusinessListRecyclerAdapter(this)

    init {
        setupObserver()
    }

    private fun setupObserver() {
        businessRepository.addObserver(this)
    }

    fun fetch() {
        if ((adapter.data?.size ?: 0) > 0) {
            listener.startLoading()
        }
        businessRepository.fetchBusinesses(businessFilterViewModel.selectedFilter)
    }

    override fun onItemClick(itemView: View, business: Business?) {
        business ?: return

        val bundle = Bundle()
        bundle.putString(BusinessDetailsFragment.ARG_BUSINESS_ID, business.id)
        navigateToDetails(itemView, bundle)
    }

    private fun navigateToDetails(itemView: View, bundle: Bundle) {
        val imageView: ImageView = itemView.findViewById(R.id.businessImageView)
        val extras = FragmentNavigator.Extras.Builder()
            .addSharedElement(imageView, "businessImage")
            .build()
        Navigation.findNavController(itemView).navigate(R.id.fragmentListToDetails, bundle, null, extras)
    }

    override fun update(observable: Observable?, arg: Any?) {
        if (observable is BusinessRepository) {
            adapter.data = observable.businessList
            updateBusinessListState()
        }
    }

    private fun updateBusinessListState() {
        listener.stopLoading()
        updateLayoutManager()
        adapter.notifyDataSetChanged()
    }

    private fun updateLayoutManager() {
        if (!adapter.data.isNullOrEmpty()) {
            listener.useDataLayout()
        } else {
            listener.useNetworkErrorLayout()
        }
    }

    fun onConnectivityChange(isConnected: Boolean?) {
        if (isConnected == true) {
            fetch()
        } else {
            updateBusinessListState()
        }
    }

    override fun filterClicked() {
        listener.startLoading()
    }
}