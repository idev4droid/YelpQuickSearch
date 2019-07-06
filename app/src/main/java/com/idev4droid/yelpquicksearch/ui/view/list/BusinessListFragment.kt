package com.idev4droid.yelpquicksearch.ui.view.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.ui.view.list.model.BusinessListViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_business_list.*
import javax.inject.Inject


class BusinessListFragment : DaggerFragment() {
    @Inject
    lateinit var businessListViewModel: BusinessListViewModel

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
                }
            }
        })
    }

    private fun initRecyclerViews() {
        /*businessListFilterRecyclerView.adapter = businessListViewModel.businessFilterViewModel.adapter
        businessListFilterRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        businessListRecyclerView?.addItemDecoration(
            GridSpacingItemDecotation(
                2,
                resources.getDimensionPixelSize(R.dimen.normal_spacing),
                true
            )
        )*/

        businessListRecyclerView.adapter = businessListViewModel.businessListAdapter
    }

    /*override fun useDataLayout() {
        businessListRecyclerView?.layoutManager = GridLayoutManager(context, 2)
    }*/
}