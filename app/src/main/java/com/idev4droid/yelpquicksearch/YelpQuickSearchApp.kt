package com.idev4droid.yelpquicksearch

import android.app.Application
import com.idev4droid.yelpquicksearch.modelView.BusinessViewModel
import com.idev4droid.yelpquicksearch.utils.ConnectivityReceiver

class YelpQuickSearchApp: Application(), ConnectivityReceiver.ConnectivityReceiverListener {
    companion object {
        var businessViewModel = BusinessViewModel()
    }

    init {
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        businessViewModel.fetchBusinesses()
    }
}