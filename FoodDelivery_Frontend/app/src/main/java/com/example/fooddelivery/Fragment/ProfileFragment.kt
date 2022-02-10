package com.example.fooddelivery.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.Room.Service.RoomServiceBuilder

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val usernameView = view.findViewById<TextView>(R.id.username_display_id)
        val emailView = view.findViewById<TextView>(R.id.email_display_id)

        val extras = activity!!.intent.extras
        if (extras != null) {
            val username = extras.getString("username")

            val db= RoomServiceBuilder.buildRooomDb(container!!.context)
            val userDao = db.userDao()
            //val allUser = userDao.getAll()
            val user= userDao.findByName(username!!)

            usernameView.text=user.name
            emailView.text=user.email
        }

        return view
    }

}