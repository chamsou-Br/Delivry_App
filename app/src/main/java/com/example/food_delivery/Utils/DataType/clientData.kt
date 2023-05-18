package com.example.food_delivery.Utils.DataType

data class clientData(var fullName : String? = null, var email : String, var password : String? = null, var phone : String? = null, var address : String? = null, var picture : String? = null, var token : String? = null) {
    constructor(email: String, password: String) : this(email = email) {
        this.password = password
    }
}
