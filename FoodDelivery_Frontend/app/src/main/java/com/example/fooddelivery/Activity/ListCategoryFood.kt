package com.example.fooddelivery.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Adapter.PopularAdapter
import com.example.fooddelivery.Domain.CategoryDomain
import com.example.fooddelivery.Domain.FoodDomain
import com.example.fooddelivery.Network.Dtos.FoodDto
import com.example.fooddelivery.Network.RestApi.ApiInterface
import com.example.fooddelivery.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ListCategoryFood : AppCompatActivity() {
    private var recyclerViewFoodList: RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var categoryText:TextView?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_category_food)

        val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerViewFoodList = findViewById(R.id.food_recyclerview)
        recyclerViewFoodList?.layoutManager = linearLayoutManager

        categoryText=findViewById(R.id.category_text)

        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            val categoryDomain:CategoryDomain = intent.getSerializableExtra("category") as CategoryDomain
            val categoryId = intent.extras?.get("categoryId") as Int
            categoryText!!.text=categoryDomain.title



            val apiInterface= ApiInterface.create().getAllFood()
            apiInterface.enqueue( object : Callback<List<FoodDto>> {
                override fun onResponse(call: Call<List<FoodDto>>?, response: Response<List<FoodDto>>?) {

                    if(response?.body() != null)
                        print(response.body())
                    if (response != null) {
                        response.body()?.let { showCategoryFood(it,categoryId) }
                    }
                }

                override fun onFailure(call: Call<List<FoodDto>>?, t: Throwable?) {
                    print(t?.message)
                }
            })

        }

    }

    private fun showCategoryFood(foodDtoList: List<FoodDto>, categoryId:Int) {
        val foodlist: ArrayList<FoodDomain> = ArrayList<FoodDomain>()
        val categoryFood:List<FoodDto> = foodDtoList.filter { foodDto -> foodDto.categoryId==categoryId+1 }

        categoryFood.forEach { foodDto: FoodDto ->
            run {
                foodlist.add(
                    FoodDomain(
                        title = foodDto.name,
                        pic = foodDto.pic,
                        description = foodDto.description,
                        fee = foodDto.price
                    )
                )
            }
        }

        adapter = PopularAdapter(foodlist)
        recyclerViewFoodList?.adapter = adapter
    }


}