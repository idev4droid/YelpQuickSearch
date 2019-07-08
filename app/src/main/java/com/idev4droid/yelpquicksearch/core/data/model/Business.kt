package com.idev4droid.yelpquicksearch.core.data.model

import com.google.gson.annotations.SerializedName

/**
 * Business is a data class that holds everything related to a business.
 * @param id The business identifier.
 * @param name The business name.
 * @param imageUrl The url to show the image in the list. It's the primary image.
 * @param isClosed Whether business has been (permanently) closed.
 * @param url The URL for business page on Yelp.
 * @param reviewCount Number of reviews for this business.
 * @param rating Rating for this business (value ranges from 1, 1.5, ... 4.5, 5).
 * @param price Price level of the business. Value is one of $, $$, $$$ and $$$$.
 * @param transactions List of Yelp transactions that the business is registered for. Current supported values are pickup, delivery and restaurant_reservation.
 * @param location Location of this business, including address, city, state, zip code and country.
 * @param distance Distance in meters from the search location. This returns meters regardless of the locale.
 * @param displayPhone Phone number of the business formatted nicely to be displayed to users. The format is the standard phone number format for the business's country.
 * @param phone Phone number of the business.
 * @param categories List of category title and alias pairs associated with this business.
 * @param photos URLs of up to three photos of the business.
 */
data class Business(
    val id: String,
    val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("is_closed") val isClosed: Boolean,
    val url: String,
    @SerializedName("review_count") val reviewCount: Int,
    val rating: Double,
    val price: String,
    val transactions: Array<String>,
    val location: Map<String, Any>,
    val distance: Double,
    val displayPhone: String,
    val phone: String,
    val categories: List<Category>,
    val photos: List<String>?
)