package com.idev4droid.yelpquicksearch.core.data

import com.idev4droid.yelpquicksearch.core.data.model.Business
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface BusinessService {
    @Headers("Authorization: Bearer pjmN4elx7KRpQIPFiuqAeFaIdKMT2zUSuWcQCbyGBxRjOf1EaDEHILo3F0BR6RCjGiNUR1hX21hJMwvoA6BVBGcfgYFCh52anXLIacO9cM2DHD7iL_D_pe1Fw73cXHYx")
    @GET("businesses/search")
    fun fetchBusinesses(@Query("term") term: String?, @Query("latitude") latitude: Double, @Query("longitude") longitude: Double): Observable<BusinessResponse>

    @Headers("Authorization: Bearer pjmN4elx7KRpQIPFiuqAeFaIdKMT2zUSuWcQCbyGBxRjOf1EaDEHILo3F0BR6RCjGiNUR1hX21hJMwvoA6BVBGcfgYFCh52anXLIacO9cM2DHD7iL_D_pe1Fw73cXHYx")
    @GET("businesses/{id}")
    fun fetchBusiness(@Path("id") id: String): Observable<Business>
}