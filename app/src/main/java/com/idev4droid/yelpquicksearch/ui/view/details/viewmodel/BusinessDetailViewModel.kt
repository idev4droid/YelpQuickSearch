package com.idev4droid.yelpquicksearch.ui.view.details.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.core.data.BusinessService
import com.idev4droid.yelpquicksearch.core.data.model.Business
import com.idev4droid.yelpquicksearch.utils.SchedulerProvider
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class BusinessDetailViewModel @Inject constructor(
    private var businessService: BusinessService,
    private var schedulerProvider: SchedulerProvider
) :
    ViewModel() {

    var business: MutableLiveData<Business?> = MutableLiveData()
    var errorMessage: MutableLiveData<Int> = MutableLiveData()

    private var subscription: Disposable? = null

    fun fetchBusinessDetails(businessId: String) {
        subscription = businessService.fetchBusiness(businessId)
            ?.observeOn(schedulerProvider.foregroundScheduler)
            ?.subscribeOn(schedulerProvider.backgroundScheduler)
            ?.subscribe({
                business.value = it
            }, { e ->
                onRetrieveBusinessError(e)
            })
    }

    private fun onRetrieveBusinessError(exception: Throwable) {
        Log.e(BusinessDetailViewModel::javaClass.name, exception.message, exception)
        errorMessage.value = R.string.error_fetching
    }

    fun getNbReviews(context: Context?): String {
        business.value?.let { business ->
            return context?.resources?.getString(R.string.short_nb_reviewers, business.reviewCount) ?: ""
        }
        return ""
    }

    fun getPrice(): String {
        business.value?.let { business ->
            return business.price
        }
        return ""
    }

    fun getRating(context: Context?): String {
        business.value?.let { business ->
            return context?.resources?.getString(R.string.rating, business.rating) ?: ""
        }
        return ""
    }

    fun getCategories(): String {
        business.value?.let { business ->
            return business.categories.joinToString { it.title }
        }
        return ""
    }

    fun getName(): String {
        business.value?.let { business ->
            return business.name
        }
        return ""
    }

    fun getPictures(): List<String> {
        business.value?.let { business ->
            return business.photos ?: listOf(business.imageUrl)
        }
        return emptyList()
    }
}