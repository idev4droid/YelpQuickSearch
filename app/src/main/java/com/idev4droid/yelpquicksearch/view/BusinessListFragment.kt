package com.idev4droid.yelpquicksearch.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.modelView.BusinessListViewModel
import com.idev4droid.yelpquicksearch.modelView.BusinessListViewModelListener
import com.idev4droid.yelpquicksearch.observeConnectivityChange
import com.idev4droid.yelpquicksearch.ui.GridSpacingItemDecotation
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_business_list.*
import javax.inject.Inject


class BusinessListFragment : DaggerFragment(), androidx.lifecycle.Observer<Boolean>, BusinessListViewModelListener {
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

        observeConnectivityChange(this)
        initRecyclerViews()
        businessListViewModel.fetch()
    }

    private fun initRecyclerViews() {
        businessListFilterRecyclerView.adapter = businessListViewModel.businessFilterViewModel.adapter
        businessListFilterRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        businessListRecyclerView?.addItemDecoration(
            GridSpacingItemDecotation(
                2,
                resources.getDimensionPixelSize(R.dimen.normal_spacing),
                true
            )
        )

        businessListRecyclerView.adapter = businessListViewModel.adapter
    }

    override fun startLoading() {
        businessListProgressBar?.visibility = View.VISIBLE
        businessListRecyclerView?.visibility = View.GONE
    }

    override fun stopLoading() {
        businessListProgressBar?.visibility = View.GONE
        businessListRecyclerView?.visibility = View.VISIBLE
    }

    override fun useDataLayout() {
        businessListRecyclerView?.layoutManager = GridLayoutManager(context, 2)
    }

    override fun useNetworkErrorLayout() {
        businessListRecyclerView?.layoutManager = LinearLayoutManager(context)
    }

    override fun onChanged(isConnected: Boolean?) {
        businessListViewModel.onConnectivityChange(isConnected)
    }
}