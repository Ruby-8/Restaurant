package com.example.restaurant.feature.view

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurant.R
import com.example.restaurant.RestaurantApp
import com.example.restaurant.di.AppComponent
import com.example.restaurant.feature.viewmodel.RestaurantFeedViewModel
import com.example.restaurant.feature.viewmodel.RestaurantFeedViewModelFactory


private const val LOCATION_PERMISSION_REQUEST_CODE = 1
private val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
class RestaurantFeedActivity: AppCompatActivity() {


    private lateinit var viewModel: RestaurantFeedViewModel
    private lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent = RestaurantApp.appComponent

        viewModel = ViewModelProvider(
            this,
            RestaurantFeedViewModelFactory(RestaurantApp.appComponent))[RestaurantFeedViewModel::class.java]

        if (hasPermissions()) {
            loadList()
        } else {
            appComponent.permissionsService.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (hasPermissions()) {
                loadList()
            } else {
                // Handle the case where the user denies the permissions
                Toast.makeText(this, "You don't have permissions", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun hasPermissions() = appComponent.permissionsService.hasPermissions(this, permissions)

    private fun loadList() {
        appComponent.locationService.location.observe(this) {
            it ?: return@observe

            val location = it
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            val leftBtn = findViewById<ImageButton>(R.id.btnLeft)
            val rightBtn = findViewById<ImageButton>(R.id.btnRight)

            recyclerView.layoutManager = NonScrollableLinearLayoutManager(this)
            recyclerView.adapter = RestaurantRecyclerViewAdapter()

            viewModel.restaurantList.observe(this) {
                (recyclerView.adapter as RestaurantRecyclerViewAdapter).updateRestaurants(it)

                leftBtn.setOnClickListener {
                    viewModel.scrollToLeft()
                }

                rightBtn.setOnClickListener {
                    viewModel.scrollToRight()
                }
            }

            viewModel.currentPosition.observe(this) {
                recyclerView.smoothScrollToPosition(it)

                viewModel.reloadIfNeeded(location.latitude, location.longitude)
            }

            viewModel.restaurantList.value?.let {
                if (it.isEmpty()) {
                    viewModel.loadRestaurants(location.latitude, location.longitude, 0)
                }
            } ?: viewModel.loadRestaurants(location.latitude, location.longitude, 0)
        }
        appComponent.locationService.getCurrentLocation()
    }
}