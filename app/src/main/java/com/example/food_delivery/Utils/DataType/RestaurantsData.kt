package com.example.food_delivery.Utils.DataType

data class RestaurantsData(val _id : String , val name:String,  var logoUrl : String, var adress : String,
                           var mapX : String, var mapY : String, var type : String,
                           var  avg : Float, var review : Int, var phone : String, var email  : String,
                           var fbApp : String, var fbUrl : String,
                           var instApp : String, var instUrl : String ,
                            var delivry : String? = null , var codePromo : String? = null) {
    constructor(data : RestaurantsData) :  this(
        data._id,
        data.name,
        data.logoUrl,
        data.adress,
        data.mapX,
        data.mapY,
        data.type,
        data.avg,
        data.review,
        data.phone,
        data.email,
        data.fbApp,
        data.fbUrl,
        data.instApp,
        data.instUrl,
        data.delivry ,
        data.codePromo
    )
}