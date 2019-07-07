package com.idev4droid.yelpquicksearch.core.data.model

/**
 * BusinessFilter is a data class that holds everything related to a business filters.
 * @param id The filter's identifier
 * @param image The filter's image
 * @param term The filter's term. This is used to call the api when filtering.
 */
data class BusinessFilter(
    val id: String,
    val image: Int?,
    val term: String?
)