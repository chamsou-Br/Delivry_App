package com.example.food_delivery.services

import com.example.food_delivery.Utils.DataType.MenuData
import com.example.food_delivery.Utils.DataType.RestaurantsData
import com.example.food_delivery.Utils.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface restaurantServiceAPI {

    @GET("/restaurants")
    suspend fun getAllRestaurants(): Response<List<RestaurantsData>>

    @POST("/restaurants")
    suspend fun addRestaurant(@Body rest: RestaurantsData): String

    @GET("/restaurants/{id}")
    suspend fun getRestaurantById(@Path("id") id: String): Response<RestaurantsData>

    companion object {
        @Volatile
        var endpoint: restaurantServiceAPI? = null
        fun createRestaurantServiceAPI(): restaurantServiceAPI {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(restaurantServiceAPI::class.java)
                }
            }
            return endpoint!!
        }


    }
}