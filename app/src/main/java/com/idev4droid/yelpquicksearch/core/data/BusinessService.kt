package com.idev4droid.yelpquicksearch.core.data

import com.idev4droid.yelpquicksearch.core.data.model.Business
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * BusinessService contains all calls to the Yelp api using Retrofit
 */
interface BusinessService {
    /**
     * Fetch all businesses on the provided search criteria
     * @param term Optional. Search term, for example "food" or "restaurants". The term may also be business names, such as "Starbucks". If term is not included the endpoint will default to searching across businesses from a small number of popular categories.
     * @param latitude Latitude of the location you want to search nearby.
     * @param longitude Longitude of the location you want to search nearby.
     * @param limit Number of business results to return.
     * @param offset Optional. Offset the list of returned business results by this amount.
     */
    @Headers("Authorization: Bearer pjmN4elx7KRpQIPFiuqAeFaIdKMT2zUSuWcQCbyGBxRjOf1EaDEHILo3F0BR6RCjGiNUR1hX21hJMwvoA6BVBGcfgYFCh52anXLIacO9cM2DHD7iL_D_pe1Fw73cXHYx")
    @GET("businesses/search")
    fun fetchBusinesses(
        @Query("term") term: String?, @Query("latitude") latitude: Double, @Query("longitude") longitude: Double, @Query(
            "limit"
        ) limit: Int, @Query("offset") offset: Int?
    ): Observable<BusinessResponse>

    /**
     * returns detailed business content
     * @param id Unique Yelp ID for the business to find
     */
    @Headers("Authorization: Bearer pjmN4elx7KRpQIPFiuqAeFaIdKMT2zUSuWcQCbyGBxRjOf1EaDEHILo3F0BR6RCjGiNUR1hX21hJMwvoA6BVBGcfgYFCh52anXLIacO9cM2DHD7iL_D_pe1Fw73cXHYx")
    @GET("businesses/{id}")
    fun fetchBusiness(@Path("id") id: String): Maybe<Business>
}