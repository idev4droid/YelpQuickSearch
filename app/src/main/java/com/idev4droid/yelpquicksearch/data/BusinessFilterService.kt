package com.idev4droid.yelpquicksearch.data

import com.idev4droid.yelpquicksearch.model.BusinessFilter

object BusinessFilterService {
    val filters = mutableListOf(
        BusinessFilter("restaurant", null, "restaurants"),
        BusinessFilter("delis", null, "delis"),
        BusinessFilter("bars", null, "bars"),
        BusinessFilter("coffee", null, "coffee")
    )
}