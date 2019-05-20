package com.idev4droid.yelpquicksearch.utils

import android.content.Context

fun Context.getString(resourceName: String): String {
    val packageName = packageName
    val resId = resources.getIdentifier(resourceName, "string", packageName)
    return getString(resId)
}