package com.example.restaurant.di

import com.example.restaurant.data.repository.RestaurantRepository
import java.util.concurrent.ExecutorService

data class AppComponent(
    val restaurantRepository: RestaurantRepository,
    val newSingleThreadExecutor: ExecutorService,
)