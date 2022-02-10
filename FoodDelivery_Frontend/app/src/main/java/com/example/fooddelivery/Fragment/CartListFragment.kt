package com.example.fooddelivery.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Adapter.CartListAdapter
import com.example.fooddelivery.Domain.FoodDomain
import com.example.fooddelivery.Helper.ManagementCart
import com.example.fooddelivery.Interface.ChangeNumberItemsListener
import com.example.fooddelivery.Network.Dtos.FoodDto
import com.example.fooddelivery.Network.RestApi.ApiInterface
import com.example.fooddelivery.R
import com.example.fooddelivery.Room.Entities.FoodOrder
import com.example.fooddelivery.Room.Service.RoomServiceBuilder
import com.example.fooddelivery.SharedPreferences.UserSharedPreferencesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import kotlin.math.round
import kotlin.math.roundToInt

class CartListFragment : Fragment() {
    private var adapter: RecyclerView.Adapter<*>? = null
    private var recyclerViewList: RecyclerView? = null
    private var managementCart: ManagementCart? = null
    private var totalFeeTxt: TextView? = null
    private var taxTxt: TextView? = null
    private var deliveryTxt: TextView? = null
    private var totalTxt: TextView? = null
    private var emptyTxt: TextView? = null
    private var tax = 0.0
    private var scrollView: ScrollView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_cart_list, container, false)
        managementCart = ManagementCart(view.context)
        initView(view)
        initList(view)

        return view
    }

    private fun initList(view: View) {
        // get All User Entries
        var userFoodOrders:List<FoodOrder> = emptyList()
        val currentUsername =
            activity?.let { UserSharedPreferencesService(it).getCurrentUsername() }
        val db = context?.let { RoomServiceBuilder.buildRooomDb(it) }
        if (currentUsername != null) {
            userFoodOrders= db?.foodDao()?.findByUsername(currentUsername)!!
        }

        val apiInterface= ApiInterface.create().getAllFood()
        apiInterface.enqueue( object : Callback<List<FoodDto>> {
            override fun onResponse(call: Call<List<FoodDto>>?, response: Response<List<FoodDto>>?) {

                if(response?.body() != null)
                    print(response.body())
                if (response != null) {
                    showManagementCard(view, response.body(), userFoodOrders)
                    calculateCard()
                }
            }

            override fun onFailure(call: Call<List<FoodDto>>?, t: Throwable?) {
                print(t?.message)
            }
        })
    }

    private fun showManagementCard(view: View, allFood: List<FoodDto>?, userFood:List<FoodOrder>) {

        if (managementCart!=null)
            userFood.forEach { currentUserFood:FoodOrder ->
                run{
                    val foodTemplate =
                        allFood?.find { foodDto -> foodDto.name.lowercase() == currentUserFood.name.lowercase() }
                    if (foodTemplate != null) {
                        managementCart!!.insertFood(
                            FoodDomain(
                                title = foodTemplate.name,
                                pic = foodTemplate.pic,
                                description = foodTemplate.description,
                                foodTemplate.price, currentUserFood.amount
                            )
                        )
                    }

                }
            }

        val linearLayoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerViewList!!.layoutManager = linearLayoutManager

        adapter = managementCart?.let {
            CartListAdapter(
                it.listCard,
                view.context,
                object : ChangeNumberItemsListener {
                    override fun changed() {
                        calculateCard()
                    }
                })
        }
        recyclerViewList!!.adapter = adapter
        if (managementCart?.listCard?.isEmpty() == true) {
            emptyTxt!!.visibility = View.VISIBLE
            scrollView!!.visibility = View.GONE
        } else {
            emptyTxt!!.visibility = View.GONE
            scrollView!!.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateCard() {
        val percentTax = 0.02
        val delivery = 10.0
        tax = (managementCart?.totalFee?.times(percentTax)?.times(100.0)?.let { round(it) }?.div(100.0)!!)
        val total = ((managementCart?.totalFee?.plus(tax)?.plus(delivery))?.times(100.0)?.let { round(it) }?.div(100.0)!!)
        val itemTotal = (managementCart?.totalFee?.times(100.0)?.let { round(it) }?.div(100.0)!!)

        totalFeeTxt!!.text = "$$itemTotal"
        taxTxt!!.text = "$$tax"
        deliveryTxt!!.text = "$$delivery"
        totalTxt!!.text = "$$total"
    }

    private fun initView(view:View) {
        recyclerViewList = view.findViewById(R.id.recyclerview)
        totalFeeTxt = view.findViewById(R.id.totalFeeTxt)
        taxTxt = view.findViewById(R.id.taxTxt)
        deliveryTxt = view.findViewById(R.id.deliveryTxt)
        totalTxt = view.findViewById(R.id.totalTxt)
        emptyTxt = view.findViewById(R.id.emptyTxt)
        scrollView = view.findViewById(R.id.scrollView4)
    }

}