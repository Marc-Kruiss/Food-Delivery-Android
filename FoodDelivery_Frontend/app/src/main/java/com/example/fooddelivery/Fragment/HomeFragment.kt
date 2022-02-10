package com.example.fooddelivery.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Adapter.CategoryAdapter
import com.example.fooddelivery.Adapter.PopularAdapter
import com.example.fooddelivery.Domain.CategoryDomain
import com.example.fooddelivery.R
import com.example.fooddelivery.Room.Service.RoomServiceBuilder
import java.util.ArrayList
import android.widget.Toast
import com.example.fooddelivery.Activity.ListCategoryFood
import com.example.fooddelivery.Activity.ListSearchFood
import com.example.fooddelivery.Domain.FoodDomain
import com.example.fooddelivery.Network.RestApi.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.fooddelivery.Network.Dtos.CategoryDto
import com.example.fooddelivery.Network.Dtos.FoodDto


class HomeFragment : Fragment() {
    private var adapter: RecyclerView.Adapter<*>? = null
    private var adapter2: RecyclerView.Adapter<*>? = null
    private var recyclerViewCategoryList: RecyclerView? = null
    private var recyclerViewPopularList: RecyclerView? = null
    private lateinit var foodSearchTextView: EditText

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)!!

        //recyclerViewCategory(view)
        loadFoodDomains(view)
        loadCategoryDomains(view)
        //recyclerViewPopular(view, response.body())

        val extras = activity!!.intent.extras
        if (extras != null) {
            val username = extras.getString("username")

            val db= RoomServiceBuilder.buildRooomDb(container!!.context)
            val userDao = db.userDao()
            //val allUser = userDao.getAll()
            val user= userDao.findByName(username!!)

            val helloTextField:TextView = view.findViewById(R.id.hello_text_id)
            helloTextField.text = "Hello ${user.name}"
        }

        foodSearchTextView=view.findViewById(R.id.searchFoodTxt)
        foodSearchTextView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                val searchText = foodSearchTextView.text.trim().toString()
                if (searchText.isNotBlank()){
                    val intent = Intent(context, ListSearchFood::class.java)
                    intent.putExtra("searchtext", searchText)
                    startActivity(intent)
                }
            }
            false
        })
        return view
    }


    private fun recyclerViewPopular(view: View, foodDtoList: List<FoodDto>?) {
        val linearLayoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewPopularList = view.findViewById(R.id.recyclerView2)
        recyclerViewPopularList?.layoutManager = linearLayoutManager
        val foodlist: ArrayList<FoodDomain> = ArrayList<FoodDomain>()

        // Select random 4 Food Elements to display on popular section
        if (!foodDtoList.isNullOrEmpty()){
            val randomFoods = foodDtoList.asSequence().shuffled().take(4).toList()

            randomFoods.forEach { foodDto: FoodDto ->
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
        }
        adapter2 = PopularAdapter(foodlist)
        recyclerViewPopularList?.adapter = adapter2
    }

    private fun loadFoodDomains(view: View) {
        val apiInterface= ApiInterface.create().getAllFood()
        apiInterface.enqueue( object : Callback<List<FoodDto>>{
            override fun onResponse(call: Call<List<FoodDto>>?, response: Response<List<FoodDto>>?) {

                if(response?.body() != null)
                    print(response.body())
                if (response != null) {
                    recyclerViewPopular(view, response.body())
                }
            }

            override fun onFailure(call: Call<List<FoodDto>>?, t: Throwable?) {
                print(t?.message)
            }
        })
    }

    private fun loadCategoryDomains(view:View){
        val apiInterface= ApiInterface.create().getAllCategory()
        apiInterface.enqueue( object : Callback<List<CategoryDto>>{
            override fun onResponse(call: Call<List<CategoryDto>>?, response: Response<List<CategoryDto>>?) {

                if(response?.body() != null)
                    print(response.body())
                if (response != null) {
                    response.body()?.let { recyclerViewCategory(view, it) }
                }
            }

            override fun onFailure(call: Call<List<CategoryDto>>?, t: Throwable?) {
                print(t?.message)
            }
        })
    }

    private fun recyclerViewCategory(view: View, categoryDtos:List<CategoryDto>) {
        val linearLayoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategoryList = view.findViewById(R.id.recyclerView)
        recyclerViewCategoryList?.layoutManager = linearLayoutManager
        // list to display on fragment in recycler view
        val categoryList: ArrayList<CategoryDomain> = ArrayList<CategoryDomain>()

        // map data from database to the category data for the recyler view
        categoryDtos.forEach { categoryDto: CategoryDto ->
            run {
                categoryList.add(
                    CategoryDomain(
                        title = categoryDto.name,
                        pic = categoryDto.pic,
                    )
                )
            }
        }
        adapter = CategoryAdapter(categoryList)
        recyclerViewCategoryList?.adapter = adapter
    }


}