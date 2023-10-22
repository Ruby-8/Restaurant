package com.example.restaurant.di

import com.example.restaurant.data.repository.RestaurantRepository

data class AppComponent(val restaurantRepository: RestaurantRepository)