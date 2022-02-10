package com.example.fooddelivery.Activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fooddelivery.Domain.FoodDomain
import com.example.fooddelivery.Helper.ManagementCart
import com.example.fooddelivery.R
import com.example.fooddelivery.Room.Entities.FoodOrder
import com.example.fooddelivery.Room.Service.RoomServiceBuilder
import com.example.fooddelivery.SharedPreferences.UserSharedPreferencesService


class ShowDetailActivity : AppCompatActivity() {
    private var addToCardBtn: TextView? = null
    private var titleTxt: TextView? = null
    private var feeTxt: TextView? = null
    private var descriptionTxt: TextView? = null
    private var numberOrderTxt: TextView? = null
    private var plusBtn: ImageView? = null
    private var minusBtn: ImageView? = null
    private var picFood: ImageView? = null
    private var `object`: FoodDomain? = null
    private var numberOrder = 1
    private var managementCart: ManagementCart? = null
    private var username:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)

        username = UserSharedPreferencesService(this).getCurrentUsername()

        managementCart = ManagementCart(this)
        initView()

        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            val food = intent.getSerializableExtra("food") as FoodDomain

            this.titleTxt?.text=food.title
            this.feeTxt?.text="$ "+food.fee.toString()
            this.descriptionTxt?.text= food.description

            val drawableResourceId = applicationContext.resources.getIdentifier(
                food.pic,
                "drawable",
                applicationContext.packageName
            )
            Glide.with(applicationContext)
                .load(drawableResourceId)
                .into(picFood!!)
        }

        addToCardBtn!!.setOnClickListener {
            if (numberOrder > 1) {
                numberOrder -= 1
            }
            val amount = Integer.valueOf(numberOrderTxt!!.text.toString())

            addFoodEntry(FoodOrder(
                name= titleTxt!!.text.toString(),
                amount = amount,
                username = username.toString()
            ))
        }

        plusBtn!!.setOnClickListener {
            val amount = Integer.valueOf(numberOrderTxt!!.text.toString())
            numberOrderTxt!!.text=(amount+1).toString()
        }

        minusBtn!!.setOnClickListener {
            val amount = Integer.valueOf(numberOrderTxt!!.text.toString())
            if (amount>0)
                numberOrderTxt!!.text=(amount-1).toString()
        }
    }

    private fun initView() {
        addToCardBtn = findViewById(R.id.addToCardBtn)
        titleTxt = findViewById(R.id.titleTxt)
        feeTxt = findViewById(R.id.priceTxt)
        descriptionTxt = findViewById(R.id.descriptionTxt)
        numberOrderTxt = findViewById(R.id.numberOrderTxt)
        plusBtn = findViewById(R.id.plusBtn)
        minusBtn = findViewById(R.id.minusBtn)
        picFood = findViewById(R.id.foodPic)
    }

    private fun addFoodEntry(foodOrder: FoodOrder) {
        val db= RoomServiceBuilder.buildRooomDb(applicationContext)

        val foodDao = db.foodDao()
        foodDao.insertAll(foodOrder)
        Toast.makeText(applicationContext, foodOrder.name + " added " + foodOrder.amount +" times for "+foodOrder.username, Toast.LENGTH_SHORT).show()
    }
}