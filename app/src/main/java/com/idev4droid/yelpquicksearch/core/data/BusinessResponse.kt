package com.idev4droid.yelpquicksearch.core.data

import com.idev4droid.yelpquicksearch.core.data.model.Business
import java.util.Observable

/**
 * BusinessResponse is a transition class between the api call for fetching multiple businesses and the ViewModels
 */
class BusinessResponse : Observable() {
    var businesses: List<Business>? = null
}