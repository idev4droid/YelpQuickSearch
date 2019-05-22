package com.idev4droid.yelpquicksearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.idev4droid.yelpquicksearch.MainActivity.Companion.businessesViewModel
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.model.Business
import com.idev4droid.yelpquicksearch.modelView.BusinessDetailViewModel
import com.idev4droid.yelpquicksearch.modelView.BusinessDetailViewModelListener
import kotlinx.android.synthetic.main.fragment_business_details.*


class BusinessDetailsFragment: Fragment(), BusinessDetailViewModelListener {
    companion object {
        const val ARG_BUSINESS_ID = "BUSINESS_ID"
    }

    var business: Business? = null
    var businessDetailViewModel: BusinessDetailViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getBusinessFromBundle()

        return inflater.inflate(R.layout.fragment_business_details, container, false)
    }

    private fun getBusinessFromBundle() {
        arguments?.let { bundle ->
            val businessId = bundle.get(ARG_BUSINESS_ID)
            business = businessesViewModel.businessList.find { it.id == businessId }
            business?.let { business ->
                businessDetailViewModel = BusinessDetailViewModel(this, business)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackButton()

        bindData()
    }

    override fun bindData() {
        businessDetailViewModel?.run {
            businessDetailLargeName?.text = getName()
            businessDetailName?.text = getName()
            businessDetailRating?.text = getRating(context)
            businessDetailReviews?.text = getNbReviews(context)
            businessDetailPrice?.text = getPrice()
            businessCategories?.text = getCategories()
            context?.let {
                businessDetailImageViewPager?.adapter = BusinessDetailViewPagerAdapter(it, getPictures())
            }
        }
    }

    private fun initBackButton() {
        businessDetailBackButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }
}