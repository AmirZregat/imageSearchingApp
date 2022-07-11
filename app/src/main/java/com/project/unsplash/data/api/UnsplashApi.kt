package com.project.unsplash.data.api

import com.project.unsplash.util.Constants.Companion.CLIENT_ID
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    //here we add all our get requests
    //annotate the fun with get and pass the end point url
    //we use suspend fun cuz this interface will run in the background thread

    @Headers(
        "Accept-Version: v1",
        "Authorization: Client-ID $CLIENT_ID"
    ) //meta data that unsplash api needs and required in their documentation
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse
}