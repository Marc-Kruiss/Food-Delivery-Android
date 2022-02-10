package com.example.fooddelivery.Room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fooddelivery.Room.Entities.Address
import com.example.fooddelivery.Room.Entities.FoodOrder

@Dao
interface AddressDao {
    @Query("SELECT * FROM address")
    fun getAll(): List<Address>

    @Query("SELECT * FROM address WHERE username LIKE :username LIMIT 1")
    fun findByUser(username:String): Address?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg address: Address)

    @Query("Delete from address")
    fun deleteTable()
}