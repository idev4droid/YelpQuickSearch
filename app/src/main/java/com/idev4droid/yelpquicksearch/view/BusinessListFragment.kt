package com.idev4droid.yelpquicksearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.model.Business
import com.idev4droid.yelpquicksearch.modelView.BusinessViewModel
import kotlinx.android.synthetic.main.fragment_business_list.*
import java.util.*

class BusinessListFragment: Fragment(), BusinessListRecyclerAdapter.Listener, Observer {
    private var adapter: BusinessListRecyclerAdapter = BusinessListRecyclerAdapter(this)
    private var businessViewModel = BusinessViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_business_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setupObserver()
    }

    private fun initRecyclerView() {
        businessListRecyclerView.layoutManager = GridLayoutManager(context, 2, VERTICAL, false)
        businessListRecyclerView.adapter = adapter
    }

    private fun setupObserver(){
        businessViewModel.addObserver(this)
    }

    override fun onItemClick(business: Business?) {
    }

    override fun update(observable: Observable?, data: Any?) {
        if (observable is BusinessViewModel) {
            adapter.data = observable.businessList
            adapter.notifyDataSetChanged()
        }
    }
}