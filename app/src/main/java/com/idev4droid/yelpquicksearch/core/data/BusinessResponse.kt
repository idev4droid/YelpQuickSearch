package com.idev4droid.yelpquicksearch.core.data

import com.idev4droid.yelpquicksearch.core.data.model.Business
import java.util.Observable

class BusinessResponse : Observable() {
    var businesses: List<Business>? = null
}