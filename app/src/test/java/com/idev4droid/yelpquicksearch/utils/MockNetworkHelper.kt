package com.idev4droid.yelpquicksearch.utils

import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

fun createResponse404NotFound(): Response<HttpException> {
    return createResponseWithCodeAndMessage(404, "Not Found")
}

fun createResponseWithCodeAndMessage(responseCode: Int, message: String): Response<HttpException> {
    return Response.error(responseCode, ResponseBody.create(null, message))
}