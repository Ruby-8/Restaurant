package com.example.restaurant.feature.transformer

import com.example.restaurant.data.model.Business
import com.example.restaurant.feature.model.Restaurant

interface Transformer<INPUT, OUTPUT> {
    fun transform(input: INPUT): OUTPUT;
}

class RestaurantTransformer: Transformer<Business, Restaurant> {
    override fun transform(input: Business): Restaurant {
        return Restaurant(
            input.id,
            input.name,
            input.imageUrl,
            input.rating
        )
    }
}