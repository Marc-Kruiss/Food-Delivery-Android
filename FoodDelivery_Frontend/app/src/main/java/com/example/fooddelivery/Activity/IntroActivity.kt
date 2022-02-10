package com.example.fooddelivery.Activity

import android.annotation.SuppressLint
import com.example.fooddelivery.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.fooddelivery.Room.Entities.User
import com.example.fooddelivery.Room.Service.RoomServiceBuilder
import com.example.fooddelivery.SharedPreferences.UserSharedPreferencesService


class IntroActivity : AppCompatActivity() {
    private var startBtn: ConstraintLayout? = null
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        startBtn = findViewById(R.id.startbtn)
        val usernameField:TextView = findViewById(R.id.username_input_text_id)
        val emailField:TextView = findViewById(R.id.email_input_text_id)

        startBtn?.setOnClickListener(View.OnClickListener {
            // Check for valid Username and Email
            if (usernameField.text.isNullOrBlank()){
                Toast.makeText(this,"Please enter Username",Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if(emailField.text.isNullOrBlank()){
                Toast.makeText(this,"Please enter Email",Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            val currentUsername = SetupRoomDatabase(
                usernameField.text.trim().toString(),
                emailField.text.trim().toString()
            )
            UserSharedPreferencesService(this)
                .setCurrentUsername(currentUsername)

            startActivity(
                Intent(
                    this@IntroActivity,
                    MainActivity::class.java
                ).putExtra("username",currentUsername)
            )
        })
    }

    private fun SetupRoomDatabase(username:String, email:String): String {
        val db=RoomServiceBuilder.buildRooomDb(applicationContext)

        val userDao = db.userDao()
        val foodDao = db.userDao()
        val addressDao = db.addressDao()

        userDao.deleteTable()
        foodDao.deleteTable()
        addressDao.deleteTable()

        userDao.insertAll(User(username.lowercase(),email))

        return username
    }
}