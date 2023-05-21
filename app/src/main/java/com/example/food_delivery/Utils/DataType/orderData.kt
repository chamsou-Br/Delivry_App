package com.example.food_delivery.Utils.DataType

class orderData (
    var rest :String? = null , var address : String? = null ,
    var priceTotal : Float? = "0.0".toFloat() , var note :  String? = null ,
    var client : String? = null ,
    val orderItems: MutableList<OrderItem>,
    )


