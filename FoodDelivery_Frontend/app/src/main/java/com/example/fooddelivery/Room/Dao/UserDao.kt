package com.example.fooddelivery.Room.Dao

import androidx.room.*
import com.example.fooddelivery.Room.Entities.User


@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE name LIKE :name LIMIT 1")
    fun findByName(name:String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Delete
    fun delete(user: User)

    @Query("Delete from users")
    fun deleteTable()
}