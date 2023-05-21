package com.example.food_delivery.services

import com.example.food_delivery.Utils.DataType.MenuData
import com.example.food_delivery.Utils.DataType.orderData
import com.example.food_delivery.Utils.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface orderServiceAPI {

    @GET("/order/client/{id}")
    suspend fun getOrdersOfClient(@Path("id") id: String): Response<List<orderData>>



    @POST("/order")
    suspend fun validateOrder(@Body order: orderData): Response<orderData>

    companion object {
        @Volatile
        var endpoint: orderServiceAPI? = null
        fun createOrderServiceAPI(): orderServiceAPI {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(orderServiceAPI::class.java)
                }
            }
            return endpoint!!
        }


    }
}