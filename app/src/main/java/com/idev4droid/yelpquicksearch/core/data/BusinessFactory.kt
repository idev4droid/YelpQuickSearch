package com.idev4droid.yelpquicksearch.core.data

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BusinessFactory {
    companion object {
        private val BASE_URL = "https://api.yelp.com/v3/"
        fun create(): BusinessService {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create<BusinessService>(BusinessService::class.java)
        }
    }
}