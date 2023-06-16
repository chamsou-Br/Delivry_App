package com.example.food_delivery.ViewModal



import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_delivery.Utils.DataType.clientData
import com.example.food_delivery.Utils.DataType.tokenData
import com.example.food_delivery.services.clientServiceAPI
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ClientModal: ViewModel() {
    val client = MutableLiveData<clientData>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val isConnected = MutableLiveData<Boolean>()
    val path = MutableLiveData<Int>();

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        CoroutineScope(Dispatchers.Main).launch {
            loading.value = false
            println(throwable.message)
            errorMessage.value = "An error has occurred " + throwable.message
        }
    }


    fun login(data : clientData) {
        loading.value = true
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response =  clientServiceAPI.createClientServiceAPI().log(data);
            withContext(Dispatchers.Main) {
                println(response)
                loading.value = false
                if (response.isSuccessful && response.body() != null) {
                    client.value = response.body()
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

    fun getProfile(clientToken: tokenData) {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response =  clientServiceAPI.createClientServiceAPI().getProfile(clientToken)
            withContext(Dispatchers.Main) {
                println("login")
                println(response.body())
                if (response.isSuccessful && response.body() != null) {
                    client.value = response.body()
                    isConnected.value = true
                } else {
                    isConnected.value = false
                }
            }
        }
    }

    fun editProfile(data: clientData) {
        loading.value = true;
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response =  clientServiceAPI.createClientServiceAPI().editprofile(data)
            withContext(Dispatchers.Main) {
                println(response)
                println(response.body())
                if (response.isSuccessful && response.body() != null) {
                    client.value = response.body()
                } else {
                    errorMessage.value = "Email / password Incorrect"
                }
                loading.value = false
            }
        }
    }

    private fun getImagePath(uri: Uri,ctx : Context): String? {

        val cursor =  ctx.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }
        return null
    }

    fun uploadPicture(imageUri: Uri, token: String,ctx : Context){
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            //val imagePath = getImagePath(imageUri, ctx)
            //println(imagePath)
            println(imageUri)
            var imagePath: String? = null
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = ctx.contentResolver.query(imageUri, projection, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    imagePath = it.getString(columnIndex)
                }
            }
            val file = File(imagePath) // Convert the image Uri to a file
            println(file);
            println("file")
            // Create a request body with the image file
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            // Create a multipart request body part from the request file
            val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

            // Create a request body for the token
            val tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token)
            println(tokenBody)
            println(imagePart)
            val response =  clientServiceAPI.createClientServiceAPI().uploadImage(imagePart,tokenBody)

            withContext(Dispatchers.Main) {
                println(response)
                println(response.body())
                if (response.isSuccessful && response.body() != null) {
                    client.value = response.body()
                } else {
                    errorMessage.value = "An error has occurred"
                }
            }
        }
    }

}