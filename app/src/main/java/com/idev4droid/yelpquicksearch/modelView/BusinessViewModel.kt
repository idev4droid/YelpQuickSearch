package com.idev4droid.yelpquicksearch.modelView

import android.util.Log
import com.idev4droid.yelpquicksearch.data.BusinessFactory
import com.idev4droid.yelpquicksearch.model.Business
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class BusinessViewModel : Observable() {
    var businessList = mutableListOf<Business>()
    private var compositeDisposable = CompositeDisposable()

    fun fetchBusinesses() {
        val businessService = BusinessFactory.create()

        val disposable = businessService.fetchBusinesses("delis", 37.786882, -122.399972)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                it.businesses?.let{ businesses ->
                    changeBusinessDataSet(businesses)
                }
            }, { e ->
                Log.e(BusinessViewModel::javaClass.name, e.localizedMessage, e)
            })

        compositeDisposable.add(disposable)
    }

    private fun changeBusinessDataSet(businesses: List<Business>) {
        businessList.addAll(businesses)
        setChanged()
        notifyObservers()
    }
}