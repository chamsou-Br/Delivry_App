package com.example.food_delivery.modals.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bag(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "price") var price: Float?,
    @ColumnInfo(name = "logoUrl") val logoUrl: String?,
    @ColumnInfo(name = "descr") val descr: String?,
    @ColumnInfo(name = "qty") var qty: Int?,
    @ColumnInfo(name = "rest") val rest: Int?,

    )
