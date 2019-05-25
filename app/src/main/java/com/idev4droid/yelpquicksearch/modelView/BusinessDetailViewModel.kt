package com.idev4droid.yelpquicksearch.modelView

import android.content.Context
import androidx.lifecycle.ViewModel
import com.idev4droid.yelpquicksearch.MainActivity.Companion.businessesViewModel
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.model.Business
import java.util.Observable
import java.util.Observer


interface BusinessDetailViewModelListener {
    fun bindData()
}

class BusinessDetailViewModel(var listener: BusinessDetailViewModelListener, private var business: Business) :
    ViewModel(), Observer {
    init {
        setupObserver()
        fetchBusinessDetails()
    }

    private fun setupObserver() {
        businessesViewModel.addObserver(this)
    }

    private fun fetchBusinessDetails() {
        businessesViewModel.fetchBusinessDetails(business.id)
    }

    fun getNbReviews(context: Context?): String {
        return context?.resources?.getString(R.string.short_nb_reviewers, business.reviewCount) ?: ""
    }

    fun getPrice(): String {
        return business.price
    }

    fun getRating(context: Context?): String {
        return context?.resources?.getString(R.string.rating, business.rating) ?: ""
    }

    fun getCategories(): String {
        return business.categories.joinToString { it.title }
    }

    fun getName(): String {
        return business.name
    }

    fun getPictures(): List<String> {
        return business.photos ?: listOf(business.imageUrl)
    }

    override fun update(observable: Observable?, arg: Any?) {
        if (observable is BusinessesViewModel) {
            observable.businessList.find { it.id == business.id }?.let {
                business = it
                listener.bindData()
            }
        }
    }
}