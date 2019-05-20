package com.idev4droid.yelpquicksearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.YelpQuickSearchApp.Companion.businessViewModel
import com.idev4droid.yelpquicksearch.model.Business
import com.idev4droid.yelpquicksearch.modelView.BusinessDetailViewModel
import kotlinx.android.synthetic.main.fragment_business_details.*


class BusinessDetailsFragment: Fragment() {
    companion object {
        const val ARG_BUSINESS_ID = "BUSINESS_ID"
    }

    var business: Business? = null
    var businessDetailViewModel: BusinessDetailViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getBusinessFromBundle()
        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = ChangeBounds().apply {
            enterTransition = transition
        }

        return inflater.inflate(R.layout.fragment_business_details, container, false)
    }

    private fun getBusinessFromBundle() {
        arguments?.run {
            val businessId = get(ARG_BUSINESS_ID)
            business = businessViewModel.businessList.find { it.id == businessId }
            business?.let { business ->
                businessDetailViewModel = BusinessDetailViewModel(business)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        businessDetailBackButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        business?.let { business ->
            businessDetailName.text = business.name

        }
        businessDetailReviews.text = businessDetailViewModel?.getNbReviews(context)
        businessDetailViewModel?.loadImage(businessDetailImage)
    }
}