package com.example.myrecyclerviewapplication

import android.content.Context
import androidx.room.Room


object CityDatabaseObject {
    private var instance:CityDatabase? =null

    fun getDatabase(context:Context):CityDatabase{
        return instance?:buildDatabase(context).also{ instance = it}

    }

    private fun buildDatabase(context:Context):CityDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            CityDatabase::class.java,
            "city_database"
        ).build()
    }
}