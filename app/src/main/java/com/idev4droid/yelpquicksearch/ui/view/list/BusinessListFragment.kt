package com.idev4droid.yelpquicksearch.ui.view.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.core.data.model.Business
import com.idev4droid.yelpquicksearch.ui.base.GridSpacingItemDecoration
import com.idev4droid.yelpquicksearch.ui.view.details.BusinessDetailsFragment
import com.idev4droid.yelpquicksearch.ui.view.list.filter.viewmodel.BusinessFilterViewModel
import com.idev4droid.yelpquicksearch.ui.view.list.viewmodel.BusinessListViewModel
import com.idev4droid.yelpquicksearch.utils.observeConnectivityChange
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_business_list.*
import javax.inject.Inject

/**
 * BusinessListFragment is a fragment that displays a list of filters and business.
 * When pressing on a filter it will change the search criteria and add it as a `term` to filter out unwanted categories.
 * When pressing on a business the details page will open showing all the information about the business and quick ways to get in contact with them.
 */
class BusinessListFragment : DaggerFragment(), Observer<Boolean>, BusinessListRecyclerAdapter.Listener {
    @Inject
    lateinit var businessListViewModel: BusinessListViewModel
    @Inject
    lateinit var businessFilterViewModel: BusinessFilterViewModel

    private val businessListAdapter: BusinessListRecyclerAdapter =
        BusinessListRecyclerAdapter(this)

    private var inflatedView: View? = null
    private var firstLoad = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_business_list, container, false)
        } else {
            firstLoad = false
        }
        return inflatedView
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleFirstLoad()
    }

    private fun handleFirstLoad() {
        if (firstLoad) {
            initRecyclerViews()
            observeError()
            observeLoading()
            observeFilterChange()
            observeBusinessesChange()
            observeConnectivityChange(this)
        }
    }

    private fun observeError() {
        businessListViewModel.errorMessage.observe(this, Observer {
            if (businessListViewModel.businesses.value == null) {
                businessListNoInternetLayout.visibility = if (it == -1) View.GONE else View.VISIBLE
                businessListRecyclerView.visibility = if (it == -1) View.VISIBLE else View.GONE
            } else {
                if (it != -1) {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun observeLoading() {
        businessListViewModel.loadingVisibility.observe(this, Observer {
            when (it) {
                View.VISIBLE -> {
                    businessListAdapter.showLoading()
                }
                View.GONE -> {
                    businessListAdapter.hideLoading()
                }
            }
        })
    }

    private fun observeFilterChange() {
        businessFilterViewModel.selectedFilter.observe(this, Observer {
            businessListAdapter.data = null
            businessListViewModel.filter(it)
        })
    }

    private fun observeBusinessesChange() {
        businessListViewModel.businesses.observe(this, Observer {
            if (it != null) {
                businessListAdapter.updateBusinesses(it)
            }
        })
    }

    private fun initRecyclerViews() {
        businessListFilterRecyclerView.adapter = businessFilterViewModel.adapter
        businessListFilterRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        businessListRecyclerView?.addItemDecoration(
            GridSpacingItemDecoration(
                1,
                resources.getDimensionPixelSize(R.dimen.normal_spacing),
                true
            )
        )

        businessListRecyclerView.adapter = businessListAdapter
        businessListRecyclerView?.layoutManager = LinearLayoutManager(context)
    }

    override fun onChanged(isConnected: Boolean?) {
        businessListViewModel.loadBusinesses()
    }

    override fun onItemClick(itemView: View, business: Business?) {
        business ?: return

        val bundle = Bundle()
        bundle.putString(BusinessDetailsFragment.ARG_BUSINESS_ID, business.id)
        navigateToDetails(itemView, bundle)
    }

    private fun navigateToDetails(itemView: View, bundle: Bundle) {
        val imageView: ImageView = itemView.findViewById(R.id.businessImageView)
        val extras = FragmentNavigator.Extras.Builder()
            .addSharedElement(imageView, "businessImage")
            .build()
        Navigation.findNavController(itemView).navigate(R.id.fragmentListToDetails, bundle, null, extras)
    }

    override fun reachedEndOfList() {
        businessListViewModel.loadNextPage()
    }
}