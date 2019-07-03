package com.idev4droid.yelpquicksearch.core.data

import com.idev4droid.yelpquicksearch.core.data.model.BusinessFilter

object BusinessFilterService {
    val filters = mutableListOf(
        BusinessFilter("popular", null, null),
        BusinessFilter("restaurant", null, "restaurants"),
        BusinessFilter("delis", null, "delis"),
        BusinessFilter("bars", null, "bars"),
        BusinessFilter("coffee", null, "coffee")
    )
}