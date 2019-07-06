package com.idev4droid.yelpquicksearch.core.data

import android.util.Log
import com.idev4droid.yelpquicksearch.core.data.model.Business
import com.idev4droid.yelpquicksearch.core.data.model.BusinessFilter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusinessRepository @Inject constructor(private val businessService: BusinessService) : Observable() {
    var businessList = mutableListOf<Business>()

    fun fetchBusinesses(filter: BusinessFilter?) {
        val disposable = businessService.fetchBusinesses(filter?.term, 37.786882, -122.399972)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                it.businesses?.let { businesses ->
                    changeBusinessDataSet(businesses)
                }
            }, { e ->
                Log.e(BusinessRepository::javaClass.name, e.localizedMessage, e)
            })
    }

    fun fetchBusinessDetails(id: String) {
        val disposable = businessService.fetchBusiness(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                changeBusinessDataSet(it)
            }, { e ->
                Log.e(BusinessRepository::javaClass.name, e.localizedMessage, e)
            })
    }

    fun reset() {
        businessList = mutableListOf()
    }

    private fun changeBusinessDataSet(businesses: List<Business>) {
        businessList.addAll(businesses)
        setChanged()
        notifyObservers()
    }

    private fun changeBusinessDataSet(business: Business) {
        val foundBusinessId = businessList.indexOfFirst { it.id == business.id }
        if (foundBusinessId != -1) {
            businessList[foundBusinessId] = business
        } else {
            businessList.add(business)
        }
        setChanged()
        notifyObservers()
    }
}