package com.idev4droid.yelpquicksearch

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.idev4droid.yelpquicksearch.utils.ConnectivityLiveData

class YelpQuickSearchApp: Application(), Observer<Boolean> {
    lateinit var connectivityLiveData: ConnectivityLiveData
    var hasInternet = false

    override fun onCreate() {
        super.onCreate()
        initConnectivityLiveData()
    }

    private fun initConnectivityLiveData() {
        connectivityLiveData = ConnectivityLiveData(this)
        connectivityLiveData.observeForever(this)
    }

    override fun onChanged(connected: Boolean?) {
        hasInternet = connected ?: false
    }
}

fun Fragment.getApp(): YelpQuickSearchApp? {
    return (activity?.application as? YelpQuickSearchApp)
}

fun Fragment.observeConnectivityChange(observer: Observer<Boolean>) {
    getApp()?.connectivityLiveData?.observe(this, observer)
}