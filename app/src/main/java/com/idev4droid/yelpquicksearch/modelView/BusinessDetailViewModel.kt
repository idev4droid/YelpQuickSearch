package com.idev4droid.yelpquicksearch.modelView

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.model.Business
import kotlinx.android.synthetic.main.fragment_business_details.*

class BusinessDetailViewModel(private val business: Business) {

    fun getNbReviews(context: Context?): String {
        return context?.resources?.getString(R.string.nb_reviewers, business.reviewCount) ?: ""
    }

    fun loadImage(imageView: ImageView) {
        imageView.context?.let {
            Glide.with(it).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.yelp_placeholder).centerCrop()).load(business.imageUrl).into(imageView)
        }
    }

}