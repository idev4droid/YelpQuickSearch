package com.idev4droid.yelpquicksearch.ui.view.list.viewmodel

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.core.data.BusinessService
import com.idev4droid.yelpquicksearch.core.data.model.Business
import com.idev4droid.yelpquicksearch.core.data.model.BusinessFilter
import com.idev4droid.yelpquicksearch.ui.view.details.BusinessDetailsFragment
import com.idev4droid.yelpquicksearch.ui.view.list.BusinessListRecyclerAdapter
import com.idev4droid.yelpquicksearch.utils.SchedulerProvider
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class BusinessListViewModel @Inject constructor(
    private var businessService: BusinessService,
    private var schedulerProvider: SchedulerProvider
) : ViewModel(),
    BusinessListRecyclerAdapter.Listener {
    val businessListAdapter: BusinessListRecyclerAdapter =
        BusinessListRecyclerAdapter(this)
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val businesses: MutableLiveData<MutableList<Business>> = MutableLiveData()
    private var currentBusinessFilter: BusinessFilter? = null
    private var offset = 0
    private val limit = 20

    private var subscription: Disposable? = null

    fun loadBusinesses() {
        subscription =
            businessService.fetchBusinesses(currentBusinessFilter?.term, 37.786882, -122.399972, limit, offset)
            ?.observeOn(schedulerProvider.foregroundScheduler)
            ?.subscribeOn(schedulerProvider.backgroundScheduler)
            ?.doOnSubscribe { onRetrieveBusinessesStart() }
            ?.doOnTerminate { onRetrieveBusinessesFinish() }
            ?.subscribe({
                it.businesses?.let { businesses ->
                    onRetrieveBusinessesSuccess(businesses)
                }
            }, { e ->
                onRetrieveBusinessesError(e)
            })
    }

    private fun onRetrieveBusinessesStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveBusinessesFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveBusinessesSuccess(businesses: List<Business>) {
        val currentBusinesses = this.businesses.value ?: mutableListOf()
        currentBusinesses.addAll(businesses)
        this.businesses.value = currentBusinesses
        businessListAdapter.updateBusinesses(currentBusinesses)
    }

    private fun onRetrieveBusinessesError(exception: Throwable) {
        Log.e(BusinessListViewModel::javaClass.name, exception.message, exception)
        errorMessage.value = R.string.network_error
    }

    override fun onItemClick(itemView: View, business: Business?) {
        business ?: return

        val bundle = Bundle()
        bundle.putString(BusinessDetailsFragment.ARG_BUSINESS_ID, business.id)
        navigateToDetails(itemView, bundle)
    }

    override fun reachedEndOfList() {
        businesses.value?.let {
            offset = it.size
            loadBusinesses()
        }
    }

    private fun navigateToDetails(itemView: View, bundle: Bundle) {
        val imageView: ImageView = itemView.findViewById(R.id.businessImageView)
        val extras = FragmentNavigator.Extras.Builder()
            .addSharedElement(imageView, "businessImage")
            .build()
        Navigation.findNavController(itemView).navigate(R.id.fragmentListToDetails, bundle, null, extras)
    }

    fun filter(businessFilter: BusinessFilter?) {
        businesses.value = null
        offset = 0
        currentBusinessFilter = businessFilter
        loadBusinesses()
    }
}