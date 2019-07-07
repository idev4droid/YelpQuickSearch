package com.idev4droid.yelpquicksearch.core.data.model

/**
 * Category is a data class that holds everything related to a Category.
 * @param alias Alias of a category, when searching for business in certain categories, use alias rather than the title.
 * @param title Title of a category for display purpose.
 */
data class Category(
    val alias: String,
    val title: String
)