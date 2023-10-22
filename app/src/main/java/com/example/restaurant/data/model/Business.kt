package com.example.restaurant.data.model

import com.google.gson.annotations.SerializedName

data class BusinessList(
    @SerializedName("businesses")
    val businesses: List<Business>
)
data class Business(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("rating")
    val rating: Float)