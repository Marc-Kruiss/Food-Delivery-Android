package com.example.fooddelivery.Room.Service

import android.content.Context
import androidx.room.Room
import com.example.fooddelivery.Room.Database.RoomAppDatabase


object RoomServiceBuilder {
    fun buildRooomDb(context:Context): RoomAppDatabase {
        return Room.databaseBuilder(
            context,
            RoomAppDatabase::class.java, "fooddeliveryRoomDb"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}