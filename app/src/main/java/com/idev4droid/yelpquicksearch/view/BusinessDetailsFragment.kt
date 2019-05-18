package com.idev4droid.yelpquicksearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.YelpQuickSearchApp.Companion.businessViewModel
import com.idev4droid.yelpquicksearch.model.Business
import kotlinx.android.synthetic.main.fragment_business_details.*

class BusinessDetailsFragment: Fragment() {
    companion object {
        const val ARG_BUSINESS_ID = "BUSINESS_ID"
    }

    var business: Business? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getBusinessFromBundle(savedInstanceState)
        return inflater.inflate(R.layout.fragment_business_details, container, false)
    }

    private fun getBusinessFromBundle(savedInstanceState: Bundle?) {
        savedInstanceState?.run {
            val businessId = get(ARG_BUSINESS_ID)
            business = businessViewModel.businessList.find { it.id == businessId }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        business?.let { business ->
            businessDetailName.text = business.name
            context?.let {
                Glide.with(it).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.yelp_placeholder).centerCrop()).load(business.imageUrl).into(businessDetailImage)
            }
        }

    }
}