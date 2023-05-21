package com.example.food_delivery.ViewModal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_delivery.Utils.DataType.clientData
import com.example.food_delivery.Utils.DataType.orderData
import com.example.food_delivery.services.clientServiceAPI
import com.example.food_delivery.services.orderServiceAPI
import kotlinx.coroutines.*

class OrderModal: ViewModel()  {
    val orders = MutableLiveData<List<orderData>>()
    val isValidated = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        CoroutineScope(Dispatchers.Main).launch {
            loading.value = false
            isValidated.value = false
            errorMessage.value = "An error has occurred"
        }
    }


    fun validateOrder(data : orderData) {
        loading.value = true
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response =  orderServiceAPI.createOrderServiceAPI().validateOrder(data);
            withContext(Dispatchers.Main) {
                loading.value = false
                println(response)
                if (response.isSuccessful && response.body() != null) {
                    println("data")
                    println(response.body())
                    isValidated.value = true
                } else {
                    println(response)
                    errorMessage.value = "Order not valid :/"
                    isValidated.value = false
                }
            }
        }
    }
}