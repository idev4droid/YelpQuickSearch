package com.idev4droid.yelpquicksearch.ui.view.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.observeConnectivityChange
import com.idev4droid.yelpquicksearch.ui.base.GridSpacingItemDecoration
import com.idev4droid.yelpquicksearch.ui.view.list.filter.viewmodel.BusinessFilterViewModel
import com.idev4droid.yelpquicksearch.ui.view.list.viewmodel.BusinessListViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_business_list.*
import javax.inject.Inject


class BusinessListFragment : DaggerFragment(), Observer<Boolean> {
    @Inject
    lateinit var businessListViewModel: BusinessListViewModel
    @Inject
    lateinit var businessFilterViewModel: BusinessFilterViewModel

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
                    businessListViewModel.businessListAdapter.showLoading()
                }
                View.GONE -> {
                    businessListViewModel.businessListAdapter.hideLoading()
                }
            }
        })
    }

    private fun observeFilterChange() {
        businessFilterViewModel.selectedFilter.observe(this, Observer {
            businessListViewModel.filter(it)
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

        businessListRecyclerView.adapter = businessListViewModel.businessListAdapter
        businessListRecyclerView?.layoutManager = LinearLayoutManager(context)
    }

    override fun onChanged(isConnected: Boolean?) {
        businessListViewModel.loadBusinesses()
    }
}