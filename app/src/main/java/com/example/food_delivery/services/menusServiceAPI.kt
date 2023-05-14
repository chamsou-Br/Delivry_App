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

interface menusServiceAPI {


    @GET("/restaurants/{id}/menus")
    suspend fun getMenusOfRest(@Path("id") id: String): Response<List<MenuData>>

    @GET("/menus/{id}")
    suspend fun getMenusById(@Path("id") id: String): Response<MenuData>

    @POST("/menus")
    suspend fun addMenu(@Body menu: MenuData): String

    companion object {
        @Volatile
        var endpoint: menusServiceAPI? = null
        fun createMenuServiceAPI(): menusServiceAPI {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(menusServiceAPI::class.java)
                }
            }
            return endpoint!!
        }


    }
}