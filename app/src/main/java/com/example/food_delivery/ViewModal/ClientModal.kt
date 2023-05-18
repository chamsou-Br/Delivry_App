package com.example.food_delivery.ViewModal



import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.Utils.DataType.MenuData
import com.example.food_delivery.Utils.DataType.MenusData
import com.example.food_delivery.Utils.DataType.clientData
import com.example.food_delivery.modals.Entity.Bag
import com.example.food_delivery.services.clientServiceAPI
import com.example.food_delivery.services.menusServiceAPI
import kotlinx.coroutines.*

class ClientModal: ViewModel() {
    val client = MutableLiveData<clientData>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val isConnected = MutableLiveData<Boolean>()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        CoroutineScope(Dispatchers.Main).launch {
            loading.value = false
            errorMessage.value = "An error has occurred"
        }
    }


    fun login(data : clientData) {
        loading.value = true
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response =  clientServiceAPI.createClientServiceAPI().login(data);
            withContext(Dispatchers.Main) {
                loading.value = false
                if (response.isSuccessful && response.body() != null) {
                    client.value = response.body()
                    println("data")
                    println(response.body())
                    isConnected.value = true
                } else {
                    errorMessage.value = "Email / password Incorrect"
                    isConnected.value = false
                }
            }
        }
    }

    fun register(data : clientData) {
        loading.value = true
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response =  clientServiceAPI.createClientServiceAPI().register(data);
            withContext(Dispatchers.Main) {
                loading.value = false
                println(response)
                if (response.isSuccessful && response.body() != null) {
                    client.value = response.body()
                    println("REGISTER")
                    println(response.body())
                    isConnected.value = true
                } else {
                    println(response)
                    errorMessage.value = "Make sure you enter all the information ! "
                    isConnected.value = false
                }
            }
        }
    }

    fun modifyProfile(){

    }

}