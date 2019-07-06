package com.idev4droid.yelpquicksearch.ui.view.details

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.transition.TransitionInflater
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.ui.view.details.viewmodel.BusinessDetailViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_business_details.*
import javax.inject.Inject

class BusinessDetailsFragment : DaggerFragment() {
    companion object {
        const val ARG_BUSINESS_ID = "BUSINESS_ID"
    }

    @Inject
    lateinit var businessDetailViewModel: BusinessDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getBusinessFromBundle()

        return inflater.inflate(R.layout.fragment_business_details, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun getBusinessFromBundle() {
        arguments?.let { bundle ->
            val businessId = bundle.get(ARG_BUSINESS_ID)
            businessDetailViewModel.fetchBusinessDetails(businessId as String)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackButton()

        observeBusinessDetails()
    }

    fun observeBusinessDetails() {
        businessDetailViewModel.business.observe(this, Observer {
            if (it != null) {
                businessDetailViewModel.run {
                    businessDetailLargeName?.text = getName()
                    businessDetailName?.text = getName()
                    businessDetailRating?.text = getRating(context)
                    businessDetailReviews?.text = getNbReviews(context)
                    businessDetailPrice?.text = getPrice()
                    businessCategories?.text = getCategories()
                    context?.let {
                        businessDetailImageViewPager?.adapter =
                            BusinessDetailViewPagerAdapter(it, getPictures())
                    }
                }
            }
        })
    }

    private fun initBackButton() {
        businessDetailBackButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }
}