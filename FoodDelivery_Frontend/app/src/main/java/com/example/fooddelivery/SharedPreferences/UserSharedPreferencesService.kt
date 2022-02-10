package com.example.fooddelivery.SharedPreferences

import android.content.Context
import android.content.SharedPreferences

private const val sharedPrefFile = "usersharedpreference"

class UserSharedPreferencesService(context:Context) {
    private var sharedPref:SharedPreferences? = context.getSharedPreferences(
        sharedPrefFile, Context.MODE_PRIVATE)

    fun setCurrentUsername(name: String): String? {
        if (sharedPref == null)
            return ""
        with(sharedPref!!.edit()) {
            this?.putString("username", name.lowercase())
            this?.apply()
        }
        return sharedPref!!.getString("username", "noname")
    }

    fun getCurrentUsername(): String? {
        if (sharedPref==null)
            return ""
        with(sharedPref!!.edit()) {
            return sharedPref!!.getString("username", "")?.lowercase()
        }
    }

}