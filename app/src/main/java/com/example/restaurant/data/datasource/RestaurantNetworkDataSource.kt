package com.example.restaurant.data.datasource

import com.example.restaurant.data.model.Business
import com.example.restaurant.data.model.BusinessList
import retrofit2.Call

private const val RESTAURANT = "restaurant"
class RestaurantNetworkDataSource(private val businessNetworkService: BusinessNetworkService) {
    fun getRestaurantList(latitude: Double, longitude: Double, offset: Int = 0, limit: Int = 25): Call<BusinessList> {
        return businessNetworkService.loadRestaurants(RESTAURANT, latitude, longitude, offset, limit)
    }
}