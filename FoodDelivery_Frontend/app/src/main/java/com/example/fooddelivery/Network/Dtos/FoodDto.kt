package com.example.fooddelivery.Network.Dtos

data class FoodDto(
    var id:Int,
    var name:String,
    var price:Double,
    var description:String,
    var pic:String,
    var categoryId:Int
)
