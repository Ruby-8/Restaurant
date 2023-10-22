package com.example.restaurant.di

import com.example.restaurant.data.repository.RestaurantRepository
import com.example.restaurant.service.LocationService
import com.example.restaurant.service.PermissionsService
import java.util.concurrent.ExecutorService

data class AppComponent(
    val restaurantRepository: RestaurantRepository,
    val newSingleThreadExecutor: ExecutorService,
    val permissionsService: PermissionsService,
    val locationService: LocationService,
)