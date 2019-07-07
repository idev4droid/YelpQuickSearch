package com.idev4droid.yelpquicksearch.core.data

import com.idev4droid.yelpquicksearch.core.data.model.BusinessFilter

/**
 * BusinessFilterService holds the data for all BusinessFilters.
 */
object BusinessFilterService {
    /**
     * holds all BusinessFilters
     */
    val filters = mutableListOf(
        BusinessFilter("popular", null, null),
        BusinessFilter("restaurant", null, "restaurants"),
        BusinessFilter("delis", null, "delis"),
        BusinessFilter("bars", null, "bars"),
        BusinessFilter("coffee", null, "coffee")
    )
}