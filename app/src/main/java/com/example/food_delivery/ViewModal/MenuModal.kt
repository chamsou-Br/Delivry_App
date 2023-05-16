package com.example.food_delivery.ViewModal

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_delivery.Adapters.adapterMenus
import com.example.food_delivery.Utils.DataType.MenuData
import com.example.food_delivery.Utils.DataType.MenusData
import com.example.food_delivery.Utils.DataType.RestaurantsData
import com.example.food_delivery.services.menusServiceAPI
import com.example.food_delivery.services.restaurantServiceAPI
import kotlinx.coroutines.*

class MenuModal:ViewModel() {
    val menus = MutableLiveData<List<MenuData>>()
    val menu = MutableLiveData<MenuData>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        CoroutineScope(Dispatchers.Main).launch {
            loading.value = false
            errorMessage.value = "Une erreur s'est produite"
        }
    }

    fun loadMenus (id : String){
    if (menus.value == null || menus.value!!.isEmpty() || menus.value!![0].rest != id) {
        loading.value = true
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = menusServiceAPI.createMenuServiceAPI().getMenusOfRest(id);
            withContext(Dispatchers.Main) {
                loading.value = false
                if (response.isSuccessful && response.body() != null) {
                    menus.value = response.body()
                } else {
                    errorMessage.value = "Une erreur s'est produite"
                }
            }
        }
    }
    }



    fun loadMenu (id : String) {
        if (menu.value == null || menu.value!!._id != id) {
            loading.value = true
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response = menusServiceAPI.createMenuServiceAPI().getMenusById(id);
                withContext(Dispatchers.Main) {
                    loading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        var body = response.body()
                        if (body != null) {
                            menu.value = MenuData(
                                _id = body._id,
                                rest = body.rest,
                                name = body.name,
                                logoUrl = body.logoUrl,
                                desc = body.desc,
                                avg = body.avg,
                                type = body.type,
                                calories = body.calories,
                                size = body.size,
                                cooking = body.cooking
                            )
                        }
                    } else {
                        errorMessage.value = "Une erreur s'est produite"
                    }
                }
            }
        }
    }



}