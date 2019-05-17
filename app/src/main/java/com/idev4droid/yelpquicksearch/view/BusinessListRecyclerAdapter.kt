package com.idev4droid.yelpquicksearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.model.Business
import kotlinx.android.synthetic.main.recycler_view_business_list_item.view.*

class BusinessListRecyclerAdapter(private val listener : Listener) : RecyclerView.Adapter<BusinessListRecyclerAdapter.ViewHolder>() {
    var data: List<Business>? = null

    interface Listener {
        fun onItemClick(business: Business?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_business_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data?.get(position), listener)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(business: Business?, listener: Listener) {
            business?.let {
                itemView.businessNameTextView.text = it.name
                Glide.with(itemView.context).load(it.imageUrl).into(itemView.businessImageView)
            }

            itemView.setOnClickListener{ listener.onItemClick(business) }
        }
    }
}