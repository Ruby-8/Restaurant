package com.example.restaurant.data.repository

import com.example.restaurant.data.model.Business
import com.example.restaurant.data.datasource.RestaurantNetworkDataSource

class RestaurantRepository(private val restaurantAPIDataSource: RestaurantNetworkDataSource) {
    fun getRestaurantList(latitude: Double, longitude: Double, offset: Int = 0, limit: Int = 25): Response<List<Business>?> {
        val response = restaurantAPIDataSource.getRestaurantList(latitude, longitude, offset, limit).execute()
        return if (response.isSuccessful) {
            Response.Success(response.body()?.businesses)
        } else {
            Response.Error(Throwable(response.message()))
        }
    }
}