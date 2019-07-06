package com.idev4droid.yelpquicksearch.ui.view.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.ui.base.GridSpacingItemDecotation
import com.idev4droid.yelpquicksearch.ui.view.list.filter.viewmodel.BusinessFilterViewModel
import com.idev4droid.yelpquicksearch.ui.view.list.viewmodel.BusinessListViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_business_list.*
import javax.inject.Inject


class BusinessListFragment : DaggerFragment() {
    @Inject
    lateinit var businessListViewModel: BusinessListViewModel
    @Inject
    lateinit var businessFilterViewModel: BusinessFilterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_business_list, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViews()
        observeLoading()
        observerError()
        observeFilterChange()
    }

    private fun observerError() {
        businessListViewModel.errorMessage.observe(this, Observer {
            businessListRecyclerView?.layoutManager = LinearLayoutManager(context)
        })
    }

    private fun observeLoading() {
        businessListViewModel.loadingVisibility.observe(this, Observer {
            when (it) {
                View.VISIBLE -> {
                    businessListProgressBar?.visibility = View.VISIBLE
                    businessListRecyclerView?.visibility = View.GONE
                }
                View.GONE -> {
                    businessListProgressBar?.visibility = View.GONE
                    businessListRecyclerView?.visibility = View.VISIBLE
                    businessListRecyclerView?.layoutManager = GridLayoutManager(context, 2)
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
            GridSpacingItemDecotation(
                2,
                resources.getDimensionPixelSize(R.dimen.normal_spacing),
                true
            )
        )

        businessListRecyclerView.adapter = businessListViewModel.businessListAdapter
    }
}