package com.example.fooddelivery.Room.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="address")
class Address (
    @PrimaryKey
    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "city")
    var city: String,

    @ColumnInfo(name = "zip")
    var zip: String

)