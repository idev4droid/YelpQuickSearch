package com.idev4droid.yelpquicksearch.data

import com.idev4droid.yelpquicksearch.model.BusinessFilter

object BusinessFilterService {
    val filters = mutableListOf(
        BusinessFilter("restaurant", null, listOf("restaurants", "delis")),
        BusinessFilter("delis", null, listOf("delis", "sandwiches")),
        BusinessFilter("bars", null, listOf("bars", "nightlife")),
        BusinessFilter("coffee", null, listOf("coffee", "coffeeroasteries"))
    )
}