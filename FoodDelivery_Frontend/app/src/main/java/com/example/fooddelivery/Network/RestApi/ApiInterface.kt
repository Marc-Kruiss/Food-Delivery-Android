package com.example.fooddelivery.Network.RestApi

import com.example.fooddelivery.Network.Dtos.CategoryDto
import com.example.fooddelivery.Network.Dtos.FoodDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("category/get/2")
    fun getCategory() : Call<CategoryDto>

    @GET("food/get/all")
    fun getAllFood():Call<List<FoodDto>>

    @GET("category/get/all")
    fun getAllCategory():Call<List<CategoryDto>>

    companion object {

        var BASE_URL = "http://10.0.2.2:8080/api/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}