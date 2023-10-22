package com.example.restaurant.feature.transformer

import com.example.restaurant.data.model.RestaurantBO
import com.example.restaurant.feature.model.Restaurant

interface Transformer<INPUT, OUTPUT> {
    fun transform(input: INPUT): OUTPUT;
}

class RestaurantTransformer: Transformer<RestaurantBO, Restaurant> {
    override fun transform(input: RestaurantBO): Restaurant {
        return Restaurant(
            input.id,
            input.name,
            input.imageUrl,
            input.rating
        )
    }
}