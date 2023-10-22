package com.example.restaurant.feature.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurant.data.repository.RestaurantRepository
import com.example.restaurant.di.AppComponent
import com.example.restaurant.feature.model.Restaurant
import com.example.restaurant.feature.transformer.RestaurantTransformer
import java.util.concurrent.ExecutorService

class RestaurantFeedViewModel(private val restaurantRepository: RestaurantRepository,
                              private val networkThreadPool: ExecutorService,
                              private val transformer: RestaurantTransformer = RestaurantTransformer()): ViewModel() {

    private val _restaurantList = MutableLiveData<List<Restaurant>>()
    val restaurantList: LiveData<List<Restaurant>> = _restaurantList

    private val _restaurantFeedOffset: Int = 0

    fun loadRestaurants(latitude: Float, longitude: Float, offset: Int = _restaurantFeedOffset) {
        networkThreadPool.submit {
            val restaurants = restaurantRepository.getRestaurantList(latitude, longitude, _restaurantFeedOffset)
            var restaurantList = restaurants.data?.map { transformer.transform(it) } ?: emptyList()
            restaurantList = if (offset == 0) {
                restaurantList
            } else {
                _restaurantList.value?.toMutableList()?.addAll(restaurantList)
                _restaurantList as List<Restaurant>
            }
            _restaurantList.postValue(restaurantList)
        }
    }

}

class RestaurantFeedViewModelFactory(private val appComponent: AppComponent): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantFeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestaurantFeedViewModel(appComponent.restaurantRepository, appComponent.newSingleThreadExecutor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}