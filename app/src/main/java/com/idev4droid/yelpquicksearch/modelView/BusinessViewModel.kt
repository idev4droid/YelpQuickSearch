package com.idev4droid.yelpquicksearch.modelView

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.data.BusinessFactory
import com.idev4droid.yelpquicksearch.model.Business
import com.idev4droid.yelpquicksearch.model.BusinessFilter
import com.idev4droid.yelpquicksearch.view.BusinessDetailsFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class BusinessViewModel : Observable() {
    var selectedFilter: BusinessFilter? = null
    var businessList = mutableListOf<Business>()
    private var compositeDisposable = CompositeDisposable()

    fun fetchBusinesses(filter: BusinessFilter? = selectedFilter) {
        val businessService = BusinessFactory.create()

        val disposable = businessService.fetchBusinesses(filter?.term, 37.786882, -122.399972)
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

    fun filterClicked(filter: BusinessFilter?) {
        selectedFilter = filter
        businessList = mutableListOf()
        fetchBusinesses()
    }

    fun businessClicked(itemView: View, business: Business?) {
        business ?: return

        val bundle = Bundle()
        bundle.putString(BusinessDetailsFragment.ARG_BUSINESS_ID, business.id)
        navigateToDetails(itemView, bundle)
    }

    private fun navigateToDetails(itemView: View, bundle: Bundle) {
        val extras = FragmentNavigator.Extras.Builder()
            .addSharedElement(itemView.findViewById(R.id.businessImageView), "businessImage")
            .build()
        Navigation.findNavController(itemView).navigate(R.id.fragmentListToDetails, bundle, null, extras)
    }

    private fun changeBusinessDataSet(businesses: List<Business>) {
        businessList.addAll(businesses)
        setChanged()
        notifyObservers()
    }
}