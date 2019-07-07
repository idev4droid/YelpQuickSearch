package com.idev4droid.yelpquicksearch.core.data.model

import com.google.gson.annotations.SerializedName

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
    val locations: Map<String, Double>,
    val distance: Double,
    val displayPhone: String,
    val phone: String,
    val categories: List<Category>,
    val photos: List<String>?
)