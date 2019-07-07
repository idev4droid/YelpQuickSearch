package com.idev4droid.yelpquicksearch.utils

import android.content.Context

/**
 * returns a string using a stringed resource id
 */
fun Context.getString(resourceName: String): String {
    val packageName = packageName
    val resId = resources.getIdentifier(resourceName, "string", packageName)
    return getString(resId)
}