package com.example.restaurant.service

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LocationService(context: Context) {

    private var locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val _location = MutableLiveData<Location?>()
    val location: LiveData<Location?> get() = _location

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        val lastKnownLocation: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            _location.value = lastKnownLocation
        } else {
            locationManager.requestSingleUpdate(
                LocationManager.GPS_PROVIDER,
                { location -> _location.value = location },
                null
            )
        }
    }
}

