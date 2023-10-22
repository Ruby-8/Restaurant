package com.example.restaurant.data.repository

import com.example.restaurant.data.model.RestaurantBO
import com.example.restaurant.data.datasource.RestaurantNetworkDataSource

class RestaurantRepository(private val restaurantAPIDataSource: RestaurantNetworkDataSource) {
    fun getRestaurantList(latitude: Float, longitude: Float, offset: Int = 0, limit: Int = 25): Response<List<RestaurantBO>?> {
        val response = restaurantAPIDataSource.getRestaurantList(latitude, longitude, offset, limit).execute()
        return if (response.isSuccessful) {
            Response.Success(response.body())
        } else {
            Response.Error(Throwable(response.message()))
        }
    }
}