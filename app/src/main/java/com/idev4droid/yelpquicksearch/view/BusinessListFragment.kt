package com.idev4droid.yelpquicksearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.YelpQuickSearchApp.Companion.businessViewModel
import com.idev4droid.yelpquicksearch.model.Business
import com.idev4droid.yelpquicksearch.model.BusinessFilter
import com.idev4droid.yelpquicksearch.modelView.BusinessViewModel
import kotlinx.android.synthetic.main.fragment_business_list.*
import java.util.*


class BusinessListFragment: Fragment(), BusinessListRecyclerAdapter.Listener, Observer, BusinessFilterListRecyclerAdapter.Listener {

    private var filterAdapter: BusinessFilterListRecyclerAdapter = BusinessFilterListRecyclerAdapter(this)
    private var listAdapter: BusinessListRecyclerAdapter = BusinessListRecyclerAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_business_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViews()
        setupObserver()
    }

    override fun onResume() {
        super.onResume()
        fetchBusinesses()
    }

    private fun initRecyclerViews() {
        businessListFilterRecyclerView.adapter = filterAdapter
        businessListFilterRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        businessListRecyclerView.adapter = listAdapter
    }

    private fun fetchBusinesses() {
        listAdapter.data = businessViewModel.businessList
        listAdapter.notifyDataSetChanged()
        startLoading()
        businessViewModel.fetchBusinesses()
    }

    private fun startLoading() {
        if (businessViewModel.businessList.size == 0) {
            businessListProgressBar?.visibility = View.VISIBLE
            businessListRecyclerView?.visibility = View.GONE
        }
    }

    private fun stopLoading() {
        businessListProgressBar?.visibility = View.GONE
        businessListRecyclerView?.visibility = View.VISIBLE
    }

    private fun setupObserver(){
        businessViewModel.addObserver(this)
    }

    override fun onItemClick(itemView: View, business: Business?) {
        businessViewModel.businessClicked(itemView, business)
    }

    override fun onItemClick(itemView: View, filter: BusinessFilter?) {
        businessViewModel.filterClicked(filter)
        startLoading()
    }

    override fun update(observable: Observable?, data: Any?) {
        if (observable is BusinessViewModel) {
            listAdapter.data = observable.businessList
            stopLoading()
            updateLayoutManager()
            listAdapter.notifyDataSetChanged()
        }
    }

    private fun updateLayoutManager() {
        if (!listAdapter.data.isNullOrEmpty()) {
            useDataLayout()
        } else {
            useNetworkErrorLayout()
        }
    }

    private fun useDataLayout() {
        businessListRecyclerView?.layoutManager = GridLayoutManager(context, 2)
    }

    private fun useNetworkErrorLayout() {
        businessListRecyclerView?.layoutManager = LinearLayoutManager(context)
    }
}