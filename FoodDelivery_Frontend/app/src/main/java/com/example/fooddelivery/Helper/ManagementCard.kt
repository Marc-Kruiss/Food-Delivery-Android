package com.example.fooddelivery.Helper

import android.content.Context
import android.widget.Toast
import com.example.fooddelivery.Domain.FoodDomain
import com.example.fooddelivery.Interface.ChangeNumberItemsListener
import com.example.fooddelivery.Room.Service.RoomServiceBuilder
import com.example.fooddelivery.SharedPreferences.UserSharedPreferencesService
import java.util.ArrayList


class ManagementCart(private val context: Context) {
    private val currentUserName: String? = UserSharedPreferencesService(context).getCurrentUsername()
    //private val tinyDB: TinyDB = TinyDB(context)
    val db= RoomServiceBuilder.buildRooomDb(context)
    private val savedFood:ArrayList<FoodDomain> = ArrayList<FoodDomain>()

    fun insertFood(item: FoodDomain) {
        var existAlready = false
        var n = 0
        for (i in savedFood.indices) {
            if (savedFood[i].title == item.title) {
                existAlready = true
                n = i
                break
            }
        }
        if (existAlready) {
            savedFood[n].numberInCart = item.numberInCart
        } else {
            savedFood.add(item)
        }
        //tinyDB.putListObject("CardList", listFood)
        Toast.makeText(context, "Added To Your Card", Toast.LENGTH_SHORT).show()
    }

    val listCard: ArrayList<FoodDomain>
        get() = savedFood

    fun plusNumberFood(
        listfood: ArrayList<FoodDomain>,
        position: Int,
        changeNumberItemsListener: ChangeNumberItemsListener
    ) {
        val food=listfood[position]
        val foodDao = db.foodDao()
        val allFood=foodDao.getAll()
        val selectedFood=allFood.firstOrNull { foodOrder -> foodOrder.name.lowercase() == food.title.lowercase() && foodOrder.username==currentUserName }
        if (selectedFood != null) {
            selectedFood.amount=selectedFood.amount+1
            foodDao.updateFood(selectedFood)
            food.numberInCart = food.numberInCart + 1
        }
        //tinyDB.putListObject("CardList", listfood)
        changeNumberItemsListener.changed()
    }

    fun MinusNumerFood(
        listfood: ArrayList<FoodDomain>,
        position: Int,
        changeNumberItemsListener: ChangeNumberItemsListener
    ) {
        if (listfood[position].numberInCart == 1) {
            val food=listfood[position]
            val foodDao = db.foodDao()
            val selectedFood=foodDao.getAll()
                .firstOrNull { foodOrder -> foodOrder.name.lowercase() == food.title.lowercase() && foodOrder.username==currentUserName }
            if (selectedFood != null) {
                selectedFood.amount = selectedFood.amount - 1
                foodDao.delete(selectedFood)
                food.numberInCart = food.numberInCart - 1
                listfood.removeAt(position)
            }
        } else {
                val food = listfood[position]
                val foodDao = db.foodDao()
                val selectedFood = foodDao.getAll()
                    .firstOrNull { foodOrder -> foodOrder.name.lowercase() == food.title.lowercase() && foodOrder.username == currentUserName }
                if (selectedFood != null) {
                    selectedFood.amount = selectedFood.amount - 1
                    foodDao.updateFood(selectedFood)
                    food.numberInCart = food.numberInCart - 1
                }
            }
        //tinyDB.putListObject("CardList", listfood)
        changeNumberItemsListener.changed()
    }

    val totalFee: Double
        get() {
            val listFood2 = listCard
            var fee = 0.0
            for (i in listFood2.indices) {
                fee += listFood2[i].fee * listFood2[i].numberInCart
            }
            return fee
        }

}
