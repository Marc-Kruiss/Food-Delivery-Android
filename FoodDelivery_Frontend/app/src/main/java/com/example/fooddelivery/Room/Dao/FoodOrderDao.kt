package com.example.fooddelivery.Room.Dao

import androidx.room.*
import com.example.fooddelivery.Room.Entities.FoodOrder

@Dao
interface FoodOrderDao {
    @Query("SELECT * FROM food")
    fun getAll(): List<FoodOrder>

    @Query("SELECT * FROM food WHERE name LIKE :name LIMIT 1")
    fun findByName(name:String): FoodOrder

    @Query("SELECT * FROM food WHERE username LIKE :username")
    fun findByUsername(username:String): List<FoodOrder>

    @Update
    fun updateFood(foodOrder: FoodOrder)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg foodOrder: FoodOrder)

    @Delete
    fun delete(foodOrder: FoodOrder)

    @Query("Delete from food")
    fun deleteTable()
}