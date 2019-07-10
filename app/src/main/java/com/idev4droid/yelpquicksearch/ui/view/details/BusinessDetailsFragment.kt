package com.idev4droid.yelpquicksearch.ui.view.details

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.idev4droid.yelpquicksearch.ui.view.list.viewmodel.BusinessListViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_business_details.*
import javax.inject.Inject

/**
 * BusinessDetailsFragment is a fragment that contains all the detailed information of a business.
 * It will also allow the user to call the business, to view their website and to share the website with a friend.
 *
 * It requires a business id to be passed through the bundle of the intent using the key `ARG_BUSINESS_ID`. (value must be a String)
 */
class BusinessDetailsFragment : DaggerFragment() {
    companion object {
        const val ARG_BUSINESS_ID = "BUSINESS_ID"
    }

    @Inject
    lateinit var businessDetailViewModel: BusinessDetailViewModel
    @Inject
    lateinit var businessListViewModel: BusinessListViewModel

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
            val businessId = bundle.get(ARG_BUSINESS_ID) as String
            retrieveBusinessFromListViewModel(businessId)
            businessDetailViewModel.fetchBusinessDetails(businessId)
        }
    }

    private fun retrieveBusinessFromListViewModel(businessId: String) {
        val businesses = businessListViewModel.businesses.value
        businessDetailViewModel.business.value = businesses?.find { it.id == businessId }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()

        observeBusinessDetails()
    }

    private fun initButtons() {
        initCallButton()
        initWebsiteButton()
        initShareButton()
        initBackButton()
    }

    private fun observeBusinessDetails() {
        businessDetailViewModel.business.observe(this, Observer {
            if (it != null) {
                businessDetailViewModel.run {
                    businessDetailLargeName?.text = getName()
                    businessDetailName?.text = getName()
                    businessDetailRating?.text = getRating(context)
                    businessDetailReviews?.text = getNbReviews(context)
                    businessDetailPrice?.text = getPrice()
                    businessCategories?.text = getCategories()
                    businessAddress?.text = getDisplayAddress()
                    context?.let {
                        businessDetailImageViewPager?.adapter =
                            BusinessDetailViewPagerAdapter(it, getPictures())
                        businessDetailViewPagerIndicator.setViewPager(businessDetailImageViewPager)
                    }
                }
            }
        })
    }

    private fun initCallButton() {
        businessCallButton?.setOnClickListener {
            startCallIntent()
        }
        businessCallText?.setOnClickListener {
            startCallIntent()
        }
    }

    private fun startCallIntent() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", businessDetailViewModel.getPhone(), null))
        startActivity(intent)
    }

    private fun initWebsiteButton() {
        businessWebsiteButton?.setOnClickListener {
            startWebsiteIntent()
        }
        businessWebsiteText?.setOnClickListener {
            startWebsiteIntent()
        }
    }

    private fun startWebsiteIntent() {
        val url = businessDetailViewModel.getWebsite()
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun initShareButton() {
        businessShareButton?.setOnClickListener {
            startShareIntent()
        }
        businessShareText?.setOnClickListener {
            startShareIntent()
        }
    }

    private fun startShareIntent() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, context?.getString(R.string.share_string, businessDetailViewModel.getWebsite()))
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    private fun initBackButton() {
        businessDetailBackButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack(R.id.fragmentBusinessList, false)
        }
    }
}