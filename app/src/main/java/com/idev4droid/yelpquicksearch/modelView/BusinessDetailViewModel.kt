package com.idev4droid.yelpquicksearch.modelView

import android.content.Context
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.model.Business

class BusinessDetailViewModel(private val business: Business) {

    fun getNbReviews(context: Context): String {
        return context.resources.getString(R.string.nb_reviewers, business.reviewCount)
    }

}