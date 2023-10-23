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

private const val AUTOLOAD_THRESHOLD_PERCENTAGE = 0.8
class RestaurantFeedViewModel(private val restaurantRepository: RestaurantRepository,
                              private val networkThreadPool: ExecutorService,
                              private val transformer: RestaurantTransformer = RestaurantTransformer()): ViewModel() {

    private val _restaurantList = MutableLiveData<List<Restaurant>>()
    private val _currentPosition = MutableLiveData(0)
    private val _restaurantFeedOffset: Int = 0
    private var _isAutoLoadingFinished = false;
    val restaurantList: LiveData<List<Restaurant>> = _restaurantList
    val currentPosition: LiveData<Int> = _currentPosition

    fun scrollToLeft() {
        val current = _currentPosition.value ?: 0
        if (current > 0) {
            _currentPosition.postValue(current - 1)
        }
    }

    fun scrollToRight() {
        val maxLength = _restaurantList.value?.size ?: 0
        val current = _currentPosition.value ?: 0
        if (current < maxLength - 1) {
            _currentPosition.postValue(current + 1)
        }
    }

    fun loadRestaurants(latitude: Double, longitude: Double, offset: Int = _restaurantFeedOffset) {
        networkThreadPool.submit {
            val restaurants = restaurantRepository.getRestaurantList(latitude, longitude, offset)
            var restaurantList = restaurants.data?.map { transformer.transform(it) } ?: emptyList()
            restaurantList = if (offset == 0) {
                restaurantList
            } else {
                _restaurantList.value?.plus(restaurantList) as List<Restaurant>
            }
            _restaurantList.postValue(restaurantList)
            _isAutoLoadingFinished = true
        }
    }

    fun reloadIfNeeded(latitude: Double, longitude: Double) {
        val current = currentPosition.value ?: 0
        val total = _restaurantList.value?.size ?: 0

        if (_isAutoLoadingFinished && current > total * AUTOLOAD_THRESHOLD_PERCENTAGE) {
            loadRestaurants(latitude, longitude, total)
            _isAutoLoadingFinished = false
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