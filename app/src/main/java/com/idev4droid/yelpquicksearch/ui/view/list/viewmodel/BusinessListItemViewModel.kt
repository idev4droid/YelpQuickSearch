package com.idev4droid.yelpquicksearch.ui.view.list.viewmodel

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.core.data.model.Business

/**
 * BusinessListItemViewModel is a view model purely used to facilitate the conversion between the business (the Model) and the adapter (the View).
 */
class BusinessListItemViewModel(private val business: Business) : ViewModel() {

    fun getDistance(context: Context?): String {
        return context?.getString(R.string.distance, Math.ceil(business.distance / 100)) ?: ""
    }

    fun getPrice(): String {
        return business.price
    }

    fun getNbReviews(context: Context?): String {
        return context?.resources?.getString(R.string.short_nb_reviewers, business.reviewCount) ?: ""
    }

    fun getRating(context: Context?): String {
        return context?.resources?.getString(R.string.rating, business.rating) ?: ""
    }

    fun getName(): String {
        return business.name
    }

    fun loadImage(imageView: ImageView) {
        imageView.context?.let {
            Glide.with(it)
                .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.placeholder_image).centerCrop())
                .load(business.imageUrl).into(imageView)
        }
    }
}