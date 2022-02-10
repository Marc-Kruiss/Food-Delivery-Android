package com.example.fooddelivery.Activity

import android.os.Bundle
import android.provider.Settings
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Adapter.CategoryAdapter
import com.example.fooddelivery.Adapter.PopularAdapter
import com.example.fooddelivery.Domain.CategoryDomain
import com.example.fooddelivery.Domain.FoodDomain
import com.example.fooddelivery.Fragment.CartListFragment
import com.example.fooddelivery.Fragment.HomeFragment
import com.example.fooddelivery.Fragment.ProfileFragment
import com.example.fooddelivery.Fragment.SettingsFragment
import com.example.fooddelivery.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var selectedFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectedFragment=changeFragment(HomeFragment())
        bottomNavigation()

    }

    private fun bottomNavigation() {
        val cardBtn = findViewById<FloatingActionButton>(R.id.card_btn)
        val homeBtn = findViewById<LinearLayout>(R.id.homeBtn)
        val profileBtn = findViewById<LinearLayout>(R.id.profileBtn)
        val supportBtn = findViewById<LinearLayout>(R.id.supportBtn)
        val settingsBtn = findViewById<LinearLayout>(R.id.settingsBtn)
        cardBtn.setOnClickListener {
            selectedFragment=changeFragment(CartListFragment())
        }
        homeBtn.setOnClickListener {
            selectedFragment=changeFragment(HomeFragment())
        }
        profileBtn.setOnClickListener {
            selectedFragment=changeFragment(ProfileFragment())
        }
        supportBtn.setOnClickListener {
            selectedFragment=changeFragment(HomeFragment())
        }
        settingsBtn.setOnClickListener {
            selectedFragment=changeFragment(SettingsFragment())
        }
    }

    private fun changeFragment(fragment:Fragment): Fragment {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            fragment
        ).commit()
        return fragment
    }
}