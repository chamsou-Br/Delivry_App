package com.example.movieexample.viewmodel


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_delivery.Utils.DataType.RestaurantsData
import com.example.food_delivery.Utils.DataType.reviewRest
import com.example.food_delivery.Utils.DataType.tokenData
import com.example.food_delivery.services.restaurantServiceAPI

import kotlinx.coroutines.*

class RestaurantModal:ViewModel() {

    val restaurants = MutableLiveData<List<RestaurantsData>>()
    val restaurantsSearch = MutableLiveData<List<RestaurantsData>>()
    var  restaurant = MutableLiveData<RestaurantsData>()
    val loading = MutableLiveData<Boolean>()
    val reviewLoading = MutableLiveData<Boolean>()
    val review = MutableLiveData<reviewRest>()
    val errorMessage = MutableLiveData<String>()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        CoroutineScope(Dispatchers.Main).launch   {
            loading.value = false
            errorMessage.value = "An error has occurred in rest + " + throwable
            println(throwable)
        }
    }

    fun loadRests (){
        if(restaurants.value==null) {
            loading.postValue(true)
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
               val response = restaurantServiceAPI.createRestaurantServiceAPI().getAllRestaurants()
                println(response)
                println("data")
                withContext(Dispatchers.Main) {
                    loading.postValue(false)
                    if (response.isSuccessful && response.body() != null) {
                        println(response.body())
                        restaurants.postValue(response.body())
                    } else {
                        errorMessage.value = "An error has occurred"
                    }
                }


            }
        }
    }

    fun searchRest (search : String){
            loading.postValue(true)
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                println(search)
                val response = restaurantServiceAPI.createRestaurantServiceAPI().searchRestairants(search)
                withContext(Dispatchers.Main) {
                    loading.postValue(false)
                    if (response.isSuccessful && response.body() != null) {
                        println(response.body())
                        restaurantsSearch.postValue(response.body())
                    } else {
                        errorMessage.value = "An error has occurred"
                    }
                }
        }
    }

    fun loadRest(id : String) {
        if (restaurant.value == null || restaurant.value!!._id != id){
            loading.value = true
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response =
                    restaurantServiceAPI.createRestaurantServiceAPI().getRestaurantById(id)
                withContext(Dispatchers.Main) {
                    loading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        restaurant.value = response.body()
                    } else {
                        errorMessage.value = "An error has occurred"
                    }
                }
            }
            }
    }



    fun ReviewRest(data : reviewRest,ctx : Context) {
            reviewLoading.value = true
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response =
                    restaurantServiceAPI.createRestaurantServiceAPI().addReviewRest(data)
                withContext(Dispatchers.Main) {
                    reviewLoading.value = false
                    println(response.isSuccessful)
                    println(response.body())
                    if (response.isSuccessful && response.body() != null) {
                        Toast.makeText(ctx, "your review is correctly updated !", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(ctx, "An error has occurred :(", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    fun getReviewRest(id : String,data : tokenData) {
        if (review.value == null || ( id != review.value!!.rest)) {
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                println("review")
                val response =
                    restaurantServiceAPI.createRestaurantServiceAPI().getReviewRest(id,data)
                withContext(Dispatchers.Main) {
                    println(response.body())
                    println("res")
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.rating != 0)
                            review.value = response.body()
                    }
                }
            }
        }

    }

    fun getRest(id : String) {
        if (restaurant.value == null || restaurant.value!!._id != id){
            loading.value = true
            restaurant.value = restaurants.value?.find { it._id == id }
            loading.value = false
        }
    }

}

