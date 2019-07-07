package com.idev4droid.yelpquicksearch.ui.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.core.data.model.Business
import com.idev4droid.yelpquicksearch.ui.base.BaseViewHolder
import com.idev4droid.yelpquicksearch.ui.base.LoadingViewHolder
import com.idev4droid.yelpquicksearch.ui.base.VIEW_TYPE_LOADING
import com.idev4droid.yelpquicksearch.ui.base.VIEW_TYPE_NORMAL
import com.idev4droid.yelpquicksearch.ui.view.list.viewmodel.BusinessListItemViewModel
import kotlinx.android.synthetic.main.recycler_view_business_list_item.view.*

class BusinessListRecyclerAdapter(private val listener: Listener) : RecyclerView.Adapter<BaseViewHolder>() {
    var data: MutableList<Business?>? = null
    private var indexOfAddedLoadingCell = -1

    interface Listener {
        fun onItemClick(itemView: View, business: Business?)
        fun reachedEndOfList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_view_business_list_item, parent, false)
                BusinessViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_loading_item, parent, false)
                LoadingViewHolder(view)
            }
        }
    }

    fun updateBusinesses(businesses: List<Business>) {
        this.data = businesses.toMutableList()
        indexOfAddedLoadingCell = -1
        tryNotifyDataSetChanged()
    }

    fun tryNotifyDataSetChanged() {
        try {
            notifyDataSetChanged()
        } catch (e: Exception) {
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 1
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            data == null || data?.get(position) == null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_NORMAL
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is BusinessViewHolder) {
            holder.bind(data?.get(position), listener)
            if (position == itemCount - 1) {
                listener.reachedEndOfList()
            }
        }
    }

    fun showLoading() {
        if (indexOfAddedLoadingCell == -1) {
            indexOfAddedLoadingCell = 0
            data?.let {
                indexOfAddedLoadingCell = it.size
            }
            data?.add(indexOfAddedLoadingCell, null)
            tryNotifyDataSetChanged()
        }
    }

    fun hideLoading() {
        if (indexOfAddedLoadingCell != -1) {
            data?.let {
                it.removeAt(indexOfAddedLoadingCell)
                notifyItemRemoved(indexOfAddedLoadingCell)
                indexOfAddedLoadingCell = -1
            }
        }
    }

    class BusinessViewHolder(view: View) : BaseViewHolder(view) {
        fun bind(business: Business?, listener: Listener) {
            business?.let {
                val businessListItemViewModel =
                    BusinessListItemViewModel(it)
                val context = itemView.context
                itemView.businessDistanceTextView.text = businessListItemViewModel.getDistance(context)
                itemView.businessReviewsTextView.text = businessListItemViewModel.getNbReviews(context)
                itemView.businessRatingTextView.text = businessListItemViewModel.getRating(context)
                itemView.businessPriceTextView.text = businessListItemViewModel.getPrice()
                itemView.businessNameTextView.text = businessListItemViewModel.getName()
                businessListItemViewModel.loadImage(itemView.businessImageView)
            }

            itemView.setOnClickListener { listener.onItemClick(itemView, business) }
        }
    }
}