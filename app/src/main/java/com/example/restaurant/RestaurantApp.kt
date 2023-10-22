package com.example.restaurant

import android.app.Application
import com.example.restaurant.data.datasource.BusinessNetworkService
import com.example.restaurant.data.datasource.RestaurantNetworkDataSource
import com.example.restaurant.data.repository.RestaurantRepository
import com.example.restaurant.di.AppComponent
import com.example.restaurant.di.AuthorizationInterceptor
import com.example.restaurant.di.NetworkComponent
import com.example.restaurant.service.LocationService
import com.example.restaurant.service.PermissionsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors


class RestaurantApp: Application() {
    private lateinit var networkComponent: NetworkComponent

    companion object {
        lateinit var appComponent: AppComponent
    }


    override fun onCreate() {
        super.onCreate()
        setupAppComponent()
    }

    private fun setupAppComponent() {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(AuthorizationInterceptor())

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.yelp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        networkComponent = NetworkComponent(
            retrofit
        )
        appComponent = AppComponent(
            restaurantRepository(),
            Executors.newSingleThreadExecutor(),
            PermissionsService(),
            LocationService(this)
        )
    }

    private fun restaurantRepository(): RestaurantRepository {
        val businessNetworkService = networkComponent.retrofit.create(BusinessNetworkService::class.java)
        val restaurantNetworkDataSource = RestaurantNetworkDataSource(businessNetworkService)
        return RestaurantRepository(restaurantNetworkDataSource)
    }
}