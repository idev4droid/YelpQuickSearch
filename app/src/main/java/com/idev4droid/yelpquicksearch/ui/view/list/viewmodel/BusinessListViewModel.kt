package com.idev4droid.yelpquicksearch.ui.view.list.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.core.data.BusinessService
import com.idev4droid.yelpquicksearch.core.data.model.Business
import com.idev4droid.yelpquicksearch.core.data.model.BusinessFilter
import com.idev4droid.yelpquicksearch.utils.SchedulerProvider
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * BusinessListViewModel handles loading businesses into a list with filters and paging.
 * Call `loadBusinesses` to start loading businesses into the list.
 * @constructor handled by DI
 */
@Singleton
class BusinessListViewModel @Inject constructor(
    private var businessService: BusinessService,
    private var schedulerProvider: SchedulerProvider
) : ViewModel() {
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val businesses: MutableLiveData<MutableList<Business>> = MutableLiveData()
    private var currentBusinessFilter: BusinessFilter? = null
    private var offset = 0
    private val limit = 20

    private var subscription: Disposable? = null

    /**
     * Load businesses for current filter, limit and offset.
     * Observe `businesses` to receive changes
     */
    fun loadBusinesses() {
        subscription =
            businessService.fetchBusinesses(currentBusinessFilter?.term, 37.786882, -122.399972, limit, offset)
                .observeOn(schedulerProvider.foregroundScheduler)
                .subscribeOn(schedulerProvider.backgroundScheduler)
                .doOnSubscribe { onRetrieveBusinessesStart() }
                .doOnTerminate { onRetrieveBusinessesFinish() }
                .subscribe({
                    it.businesses?.let { businesses ->
                        onRetrieveBusinessesSuccess(businesses)
                    }
                }, { e ->
                    onRetrieveBusinessesError(e)
                })
    }

    private fun onRetrieveBusinessesStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = -1
    }

    private fun onRetrieveBusinessesFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveBusinessesSuccess(businesses: List<Business>) {
        val currentBusinesses = this.businesses.value ?: mutableListOf()
        currentBusinesses.addAll(businesses)
        this.businesses.value = currentBusinesses
    }

    private fun onRetrieveBusinessesError(exception: Throwable) {
        Log.e(BusinessListViewModel::javaClass.name, exception.message, exception)
        errorMessage.value = R.string.network_error
    }

    /**
     * Load the next page of businesses with current filter, limit
     */
    fun loadNextPage() {
        businesses.value?.let {
            offset = it.size
            loadBusinesses()
        }
    }

    /**
     * Filter businesses and reset paging
     * @param businessFilter The filter used when loading businesses. If terms is null, filter will be set to popular
     */
    fun filter(businessFilter: BusinessFilter?) {
        businesses.value = null
        offset = 0

        currentBusinessFilter = businessFilter
        loadBusinesses()
    }
}