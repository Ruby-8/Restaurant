package com.example.restaurant.data.datasource

import com.example.restaurant.data.backendmodel.Restaurant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BusinessNetworkService {
    @GET("/v3/businesses/search")
    fun loadRestaurants(
        @Query("term")
        term: String,
        @Query("latitude")
        latitude: Float,
        @Query("longitude")
        longitude: Float,
        @Query("offset")
        offset: Int,
        @Query("limit")
        limit: Int
    ): Call<List<Restaurant>>

}