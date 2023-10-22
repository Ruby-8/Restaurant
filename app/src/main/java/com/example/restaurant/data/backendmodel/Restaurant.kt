package com.example.restaurant.data.backendmodel

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("rating")
    val rating: Float)