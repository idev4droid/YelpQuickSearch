package com.idev4droid.yelpquicksearch.ui.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idev4droid.yelpquicksearch.R
import com.idev4droid.yelpquicksearch.core.data.model.BusinessFilter
import com.idev4droid.yelpquicksearch.ui.BaseViewHolder
import com.idev4droid.yelpquicksearch.utils.getString
import kotlinx.android.synthetic.main.recycler_view_business_filter_list_item.view.*

class BusinessFilterListRecyclerAdapter(private val listener: Listener, private val data: List<BusinessFilter>) :
    RecyclerView.Adapter<BaseViewHolder>() {
    var selectedFilter: BusinessFilter? = null

    interface Listener {
        fun onItemClick(itemView: View, filter: BusinessFilter?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_business_filter_list_item, parent, false)
        return BusinessFilterViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is BusinessFilterViewHolder) {
            val filter = data[position]
            val isSelected = selectedFilter?.id == filter.id
            holder.bind(data[position], listener, isSelected)
        }
    }

    class BusinessFilterViewHolder(view: View) : BaseViewHolder(view) {
        fun bind(filter: BusinessFilter?, listener: Listener, isSelected: Boolean) {
            filter?.let {
                itemView.businessFilterItemName.text = itemView.context.getString(it.id)
                itemView.businessFilterItemName.isSelected = isSelected
            }

            itemView.setOnClickListener { listener.onItemClick(itemView, filter) }
        }
    }
}