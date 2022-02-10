package com.example.fooddelivery.Fragment

import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.preference.RingtonePreference
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.example.fooddelivery.R
import com.example.fooddelivery.Room.Entities.Address
import com.example.fooddelivery.Room.Entities.User
import com.example.fooddelivery.Room.Service.RoomServiceBuilder
import com.example.fooddelivery.SharedPreferences.UserSharedPreferencesService


class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_settings, container, false)
        val db= RoomServiceBuilder.buildRooomDb(view.context)
        val addressDao = db.addressDao()
        val username = UserSharedPreferencesService(view.context).getCurrentUsername()!!

        val countryTxt: EditText = view.findViewById(R.id.country_txt)
        val zipTxt: EditText = view.findViewById(R.id.zip_txt)
        val cityTxt: EditText = view.findViewById(R.id.city_txt)

        // Is already available?
        val userAddress = addressDao.findByUser(username)
        if (userAddress!=null){
            countryTxt.text=userAddress.country.toEditable()
            zipTxt.text=userAddress.zip.toEditable()
            cityTxt.text=userAddress.city.toEditable()
        }

        val saveBtn:TextView = view.findViewById(R.id.save_address_btn)
        saveBtn.setOnClickListener {
            val inputAddress:Address = Address(
                username = username,
                country = countryTxt.text.trim().toString(),
                zip = zipTxt.text.trim().toString(),
                city = cityTxt.text.trim().toString()
            )

            addressDao.insertAll(inputAddress)
            Toast.makeText(context, "Updated Address", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)



}