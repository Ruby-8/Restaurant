package com.example.restaurant.data.datasource

import com.example.restaurant.data.model.Business
import com.example.restaurant.data.model.BusinessList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BusinessNetworkService {
    @GET("/v3/businesses/search")
    fun loadRestaurants(
        @Query("term")
        term: String,
        @Query("latitude")
        latitude: Double,
        @Query("longitude")
        longitude: Double,
        @Query("offset")
        offset: Int,
        @Query("limit")
        limit: Int
    ): Call<BusinessList>

}