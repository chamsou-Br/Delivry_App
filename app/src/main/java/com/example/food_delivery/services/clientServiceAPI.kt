package com.example.food_delivery.services

import android.service.autofill.UserData
import com.example.food_delivery.Utils.DataType.MenuData
import com.example.food_delivery.Utils.DataType.clientData
import com.example.food_delivery.Utils.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface clientServiceAPI {


    @POST("/auth/register")
    suspend fun register(@Body user: clientData): Response<clientData>

    @POST("/auth/login")
    suspend fun login(@Body user: clientData): Response<clientData>

    companion object {
        @Volatile
        var endpoint: clientServiceAPI? = null
        fun createClientServiceAPI(): clientServiceAPI {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(clientServiceAPI::class.java)
                }
            }
            return endpoint!!
        }


    }
}