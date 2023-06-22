package com.example.food_delivery

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.nfc.Tag
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage




import androidx.test.core.app.ApplicationProvider
import com.example.food_delivery.Utils.DataType.tokenData
import com.example.food_delivery.services.clientServiceAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val channalId = "notification_channel"
const val channelName = "com.example.food_delivry"

class FirebaseMessageReceiver : FirebaseMessagingService() {
   // var userRepo= UsersRespository()
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Handle incoming FCM messages here


        showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
    }

    private fun showNotification(title: String?, message: String?) {
        val channelId = "default_channel_id"
        val channelName = "Default Channel"
        val notificationId = 1
        println("data")
        println(message)
        val largeIconBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_back)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo) // Set a valid small icon
            .setContentTitle(title)
            .setContentText(message)
            .setLargeIcon(largeIconBitmap)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the notification channel (required for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableLights(true)
            channel.lightColor = Color.BLUE
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, notificationBuilder.build())
    }


    override fun onCreate() {
        super.onCreate()

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    println("this should work")
        if(checkUserAuth()) {
            CoroutineScope(Dispatchers.IO).launch{
                val data = tokenData(client = getUserToken() , token = token)
                println("new token")
                println(data)
                val resutl = clientServiceAPI.createClientServiceAPI().addtokenNotif(data)
                delay(500)
            }
        }


    }


    fun getUserToken():String {//recuperer le user_id sauvegardé
        val pref = getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
        val token  = pref.getString("token_food_delivry","")
        return token.toString()
    }

    fun checkUserAuth():Boolean {//verifier si le user est authentifié
        val pref = getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
        return pref.contains("connected")
    }
}