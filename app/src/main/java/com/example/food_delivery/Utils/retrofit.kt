package com.example.food_delivery.Utils
import  com.example.food_delivery.services.restaurantServiceAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.example.com/") // Replace with your API base URL
        .addConverterFactory(GsonConverterFactory.create()) // Replace with your desired converter factory
        .build()
    val RestaurantService = retrofit.create(restaurantServiceAPI::class.java)
}