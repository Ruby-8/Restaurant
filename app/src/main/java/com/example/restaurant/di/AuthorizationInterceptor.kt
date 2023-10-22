package com.example.restaurant.di

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val BEARER_TOKEN = "Bearer itoMaM6DJBtqD54BHSZQY9WdWR5xI_CnpZdxa3SG5i7N0M37VK1HklDDF4ifYh8SI-P2kI_mRj5KRSF4_FhTUAkEw322L8L8RY6bF1UB8jFx3TOR0-wW6Tk0KftNXXYx"
class AuthorizationInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest: Request = originalRequest.newBuilder()
            .header("Authorization", BEARER_TOKEN)
            .build()
        return chain.proceed(newRequest)
    }
}