package com.idev4droid.yelpquicksearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.YelpQuickSearchApp.Companion.businessViewModel
import com.idev4droid.yelpquicksearch.model.Business
import com.idev4droid.yelpquicksearch.modelView.BusinessViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_business_list.*
import java.util.*


class BusinessListFragment: Fragment(), BusinessListRecyclerAdapter.Listener, Observer {
    private var adapter: BusinessListRecyclerAdapter = BusinessListRecyclerAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_business_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setupObserver()
    }

    private fun initRecyclerView() {
        businessListRecyclerView.adapter = adapter
        fetchBusinesses()
    }

    private fun fetchBusinesses() {
        startLoading()
        businessViewModel.fetchBusinesses()
    }

    private fun startLoading() {
        businessListProgressBar.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        businessListProgressBar.visibility = View.GONE
    }

    private fun setupObserver(){
        businessViewModel.addObserver(this)
    }

    override fun onItemClick(business: Business?) {
        business ?: return

        val bundle = Bundle()
        bundle.putString(BusinessDetailsFragment.ARG_BUSINESS_ID, business.id)
        val businessDetailsFragment = BusinessDetailsFragment()
        businessDetailsFragment.arguments = bundle

        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, businessDetailsFragment)
        fragmentTransaction?.commit()
    }

    override fun update(observable: Observable?, data: Any?) {
        if (observable is BusinessViewModel) {
            adapter.data = observable.businessList
            stopLoading()
            updateLayoutManager()
            adapter.notifyDataSetChanged()
        }
    }

    private fun updateLayoutManager() {
        if (!adapter.data.isNullOrEmpty()) {
            businessListRecyclerView.layoutManager = GridLayoutManager(context, 2)
        } else {
            businessListRecyclerView.layoutManager = LinearLayoutManager(context)
        }
    }
}