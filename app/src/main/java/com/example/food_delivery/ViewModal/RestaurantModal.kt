package com.example.movieexample.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_delivery.Utils.DataType.RestaurantsData
import com.example.food_delivery.services.restaurantServiceAPI

import kotlinx.coroutines.*

class RestaurantModal:ViewModel() {

    val restaurants = MutableLiveData<List<RestaurantsData>>()
    var  restaurant = MutableLiveData<RestaurantsData>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        CoroutineScope(Dispatchers.Main).launch   {
            loading.value = false
            errorMessage.value = "Une erreur s'est produite 1" + throwable
            println("data")
            println(throwable)
        }
    }

    fun loadRests (){
        if(restaurants.value==null) {
            loading.postValue(true)
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
               val response = restaurantServiceAPI.createRestaurantServiceAPI().getAllRestaurants()

                withContext(Dispatchers.Main) {
                    loading.postValue(false)
                    if (response.isSuccessful && response.body() != null) {
                        println(response.body())
                        restaurants.postValue(response.body())
                    } else {
                        errorMessage.value = "Une erreur s'est produite 2"
                    }
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
                        errorMessage.value = "Une erreur s'est produite 3"
                    }
                }
            }
            }
    }

}

