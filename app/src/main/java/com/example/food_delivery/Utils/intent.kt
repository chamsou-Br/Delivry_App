package com.example.food_delivery

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun openMap(ctx : Context,lat : String,lon : String){
    val data = Uri.parse("geo:$lat,$lon")
    val intent = Intent(Intent.ACTION_VIEW,data)
    ctx.startActivity(intent)
}

fun call(ctx : Context , tel : String) {
    val data = Uri.parse("tel:"+tel)
    val intent = Intent(Intent.ACTION_DIAL, data)
    ctx.startActivity(intent)
}

fun mailTo(ctx : Context , mail : String) {
    val recipientEmail = mail
    val subject = "Subject"
    val message = "Body of email."

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
    }
        ctx.startActivity(intent)
}


fun openFb(ctx: Context, appUrl:String,webUrl:String){
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(appUrl))
        ctx.startActivity(intent)
    }
    catch (e: ActivityNotFoundException)
    {
        Toast.makeText(ctx,"Erreur : Application unfound", Toast.LENGTH_SHORT).show()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
        ctx.startActivity(intent)
    }
}