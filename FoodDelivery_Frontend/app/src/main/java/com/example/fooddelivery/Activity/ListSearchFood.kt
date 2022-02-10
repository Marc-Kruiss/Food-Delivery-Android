package com.example.fooddelivery.Activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Adapter.PopularAdapter
import com.example.fooddelivery.Domain.FoodDomain
import com.example.fooddelivery.Network.Dtos.FoodDto
import com.example.fooddelivery.Network.RestApi.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.fooddelivery.R

class ListSearchFood : AppCompatActivity() {
    private var recyclerViewFoodList: RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var searchtextView: TextView?=null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_search_food)

        val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerViewFoodList = findViewById(R.id.food_recyclerview)
        recyclerViewFoodList?.layoutManager = linearLayoutManager

        searchtextView=findViewById(R.id.search_text)

        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            val searchText = intent.extras?.get("searchtext") as String
            searchtextView!!.text="Results for $searchText"



            val apiInterface= ApiInterface.create().getAllFood()
            apiInterface.enqueue( object : Callback<List<FoodDto>> {
                override fun onResponse(call: Call<List<FoodDto>>?, response: Response<List<FoodDto>>?) {

                    if(response?.body() != null)
                        print(response.body())
                    if (response != null) {
                        response.body()?.let { showSearchFood(it,searchText) }
                    }
                }

                override fun onFailure(call: Call<List<FoodDto>>?, t: Throwable?) {
                    print(t?.message)
                }
            })

        }

    }

    private fun showSearchFood(foodDtoList: List<FoodDto>, searchText:String) {
        val foodlist: ArrayList<FoodDomain> = ArrayList<FoodDomain>()
        val categoryFood:List<FoodDto> = foodDtoList.filter { foodDto -> foodDto.name.lowercase().contains(searchText.lowercase())}

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