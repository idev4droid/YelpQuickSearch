package com.idev4droid.yelpquicksearch.modelView

import android.util.Log
import com.idev4droid.yelpquicksearch.data.BusinessFactory
import com.idev4droid.yelpquicksearch.model.Business
import com.idev4droid.yelpquicksearch.model.BusinessFilter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class BusinessesViewModel : Observable() {
    var businessList = mutableListOf<Business>()
    private var compositeDisposable = CompositeDisposable()

    fun fetchBusinesses(filter: BusinessFilter?) {
        val businessService = BusinessFactory.create()

        val disposable = businessService.fetchBusinesses(filter?.term, 37.786882, -122.399972)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                it.businesses?.let{ businesses ->
                    changeBusinessDataSet(businesses)
                }
            }, { e ->
                Log.e(BusinessesViewModel::javaClass.name, e.localizedMessage, e)
            })

        compositeDisposable.add(disposable)
    }

    fun reset() {
        businessList = mutableListOf()
        compositeDisposable.clear()
    }

    private fun changeBusinessDataSet(businesses: List<Business>) {
        businessList.addAll(businesses)
        setChanged()
        notifyObservers()
    }
}