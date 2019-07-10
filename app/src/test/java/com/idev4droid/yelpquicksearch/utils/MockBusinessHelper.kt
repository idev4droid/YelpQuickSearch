package com.idev4droid.yelpquicksearch.utils

import com.idev4droid.yelpquicksearch.core.data.BusinessResponse
import com.idev4droid.yelpquicksearch.core.data.model.Business
import com.idev4droid.yelpquicksearch.core.data.model.Category
import io.reactivex.Observable

fun createTestBusinessesObservable(nbMockBusinesses: Int): Observable<BusinessResponse>? {
    return Observable.just(createTestBusinessResponse(nbMockBusinesses))
}

fun createEmptyTestBusinessesObservable(): Observable<BusinessResponse>? {
    return Observable.just(createEmptyTestBusinessResponse())
}

fun createEmptyTestBusinessResponse(): BusinessResponse {
    val businessResponse = BusinessResponse()
    businessResponse.businesses = mutableListOf<Business>()
    return businessResponse
}

fun createTestBusinessResponse(nbMockBusinesses: Int): BusinessResponse {
    val businessResponse = BusinessResponse()
    val businesses = mutableListOf<Business>()
    for (businessIndex in 0 until nbMockBusinesses) {
        businesses.add(createTestBusiness())
    }
    businessResponse.businesses = businesses
    return businessResponse
}

fun createTestBusiness(): Business {
    return Business(
        "test",
        "test",
        "https://s3-media3.fl.yelpcdn.com/bphoto/m5dL_mNk9rjSJ5jQu17hVw/o.jpg",
        false,
        "https://www.yelp.com/biz/deli-board-san-francisco?adjust_creative=JXe2agUKyxpgPIXyLHQDSQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=JXe2agUKyxpgPIXyLHQDSQ",
        123,
        5.0,
        "$$",
        arrayOf("delivery", "pickup", "restaurant_reservation"),
        mapOf(Pair("display_address", listOf("24 test road", "test city", "test country", "12345"))),
        0.0,
        "(123) 456-7890",
        "+11234567890",
        listOf(Category("delis", "Delis"), Category("sandwiches", "Sandwiches")),
        listOf(
            "https://s3-media3.fl.yelpcdn.com/bphoto/m5dL_mNk9rjSJ5jQu17hVw/o.jpg",
            "https://s3-media4.fl.yelpcdn.com/bphoto/PZbg0bUYHHXPr5IEVFkgNw/o.jpg",
            "https://s3-media2.fl.yelpcdn.com/bphoto/UeulWN1OHZrUNuIdaTs8MA/o.jpg"
        )
    )
}