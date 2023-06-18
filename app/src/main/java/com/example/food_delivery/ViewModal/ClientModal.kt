package com.example.food_delivery.ViewModal



import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
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
import java.io.FileOutputStream

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

    fun loginWithGoogle(data : clientData) {
        loading.value = true
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response =  clientServiceAPI.createClientServiceAPI().loginWithGoogle(data);
            withContext(Dispatchers.Main) {
                println(response)
                loading.value = false
                if (response.isSuccessful && response.body() != null) {
                    client.value = response.body()
                    isConnected.value = true
                } else {
                    errorMessage.value = "Email Already signin :("
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


    fun uploadPicture(imageUri: Uri,imageBitmap : Bitmap ,token: String,ctx : Context){
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {


            val file = File(ctx.cacheDir, "image.jpg")
            file.createNewFile()

            val outputStream = FileOutputStream(file)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val imagePart = MultipartBody.Part.createFormData("picture", file.name, requestFile)

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