package com.idev4droid.yelpquicksearch.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.idev4droid.yelpquicksearch.YelpQuickSearchApp

/**
 * Get current app from activity and cast it to YelpQuickSearchApp.
 */
fun Fragment.getApp(): YelpQuickSearchApp? {
    return (activity?.application as? YelpQuickSearchApp)
}

/**
 * Added connectivity changed observer to a fragment.
 */
fun Fragment.observeConnectivityChange(observer: Observer<Boolean>) {
    getApp()?.connectivityLiveData?.observe(this, observer)
}