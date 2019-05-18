package com.idev4droid.yelpquicksearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.model.Business
import com.idev4droid.yelpquicksearch.ui.BaseViewHolder
import com.idev4droid.yelpquicksearch.ui.NetworkErrorViewHolder
import com.idev4droid.yelpquicksearch.ui.VIEW_TYPE_NETWORK_ERROR
import com.idev4droid.yelpquicksearch.ui.VIEW_TYPE_NORMAL
import kotlinx.android.synthetic.main.recycler_view_business_list_item.view.*


class BusinessListRecyclerAdapter(private val listener : Listener) : RecyclerView.Adapter<BaseViewHolder>() {
    var data: List<Business>? = null

    interface Listener {
        fun onItemClick(business: Business?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == VIEW_TYPE_NORMAL) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_business_list_item, parent, false)
            BusinessViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_network_error_item, parent, false)
            NetworkErrorViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.isNullOrEmpty()) {
            VIEW_TYPE_NETWORK_ERROR
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is BusinessViewHolder) {
            holder.bind(data?.get(position), listener)
        }
    }

    class BusinessViewHolder(view : View) : BaseViewHolder(view) {
        fun bind(business: Business?, listener: Listener) {
            business?.let {
                itemView.businessNameTextView.text = it.name
                Glide.with(itemView.context).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.yelp_placeholder).centerCrop()).load(it.imageUrl).into(itemView.businessImageView)
            }

            itemView.setOnClickListener{ listener.onItemClick(business) }
        }
    }
}