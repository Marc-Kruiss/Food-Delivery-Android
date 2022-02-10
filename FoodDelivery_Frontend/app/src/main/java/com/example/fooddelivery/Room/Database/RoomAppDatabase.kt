package com.example.fooddelivery.Room.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fooddelivery.Room.Dao.AddressDao
import com.example.fooddelivery.Room.Dao.FoodOrderDao
import com.example.fooddelivery.Room.Dao.UserDao
import com.example.fooddelivery.Room.Entities.Address
import com.example.fooddelivery.Room.Entities.FoodOrder
import com.example.fooddelivery.Room.Entities.User

@Database(entities = [User::class, FoodOrder::class, Address::class], version = 2)
abstract class RoomAppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun foodDao(): FoodOrderDao
    abstract fun addressDao(): AddressDao
}