package com.example.restaurant.data.datasource

import com.example.restaurant.data.backendmodel.Restaurant
import retrofit2.Call

private const val RESTAURANT = "restaurant"
class RestaurantNetworkDataSource(private val businessNetworkService: BusinessNetworkService) {
    fun getRestaurantList(latitude: Float, longitude: Float, offset: Int = 0, limit: Int = 25): Call<List<Restaurant>> {
        return businessNetworkService.loadRestaurants(RESTAURANT, latitude, longitude, offset, limit)
    }
}