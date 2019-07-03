package com.idev4droid.yelpquicksearch

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.idev4droid.connectivity.live.data.ConnectivityLiveData
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class YelpQuickSearchApp : DaggerApplication() {
    lateinit var connectivityLiveData: ConnectivityLiveData

    override fun onCreate() {
        super.onCreate()
        initConnectivityLiveData()
    }

    private fun initConnectivityLiveData() {
        connectivityLiveData = ConnectivityLiveData(this)
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    }
}

fun Fragment.getApp(): YelpQuickSearchApp? {
    return (activity?.application as? YelpQuickSearchApp)
}

fun Fragment.observeConnectivityChange(observer: Observer<Boolean>) {
    getApp()?.connectivityLiveData?.observe(this, observer)
}