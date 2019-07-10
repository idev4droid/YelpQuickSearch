package com.idev4droid.yelpquicksearch.ui.view.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.idev4droid.yelpquicksearch.R

/**
 * BusinessDetailViewPagerAdapter is an adapter used to display the pictures at the top of the details page of a business.
 *
 * @param context the fragments context
 * @param pictures the list of pictures in a url string format that will be displayed
 */
class BusinessDetailViewPagerAdapter(context: Context, private val pictures: List<String>) : PagerAdapter() {
    private val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return pictures.size
    }

    override fun isViewFromObject(view: View, anyObject: Any): Boolean {
        return view == (anyObject as? ConstraintLayout)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = layoutInflater.inflate(R.layout.view_pager_business_detail_image_item, container, false)

        setBusinessImageForPosition(itemView, position)

        container.addView(itemView)
        return itemView
    }

    private fun setBusinessImageForPosition(itemView: View, position: Int) {
        val businessImageView: ImageView = itemView.findViewById(R.id.businessDetailViewPagerImage)
        Glide.with(itemView)
            .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.placeholder_image).centerCrop())
            .load(pictures[position]).into(businessImageView)
    }

    override fun destroyItem(container: ViewGroup, position: Int, anyObject: Any) {
        container.removeView(anyObject as? ConstraintLayout)
    }
}
