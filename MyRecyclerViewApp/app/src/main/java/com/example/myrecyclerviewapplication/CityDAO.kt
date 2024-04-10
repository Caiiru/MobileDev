package com.example.myrecyclerviewapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CityDAO {
    @Query("Select * FROM City")
    fun getAll(): ArrayList<City>

    @Insert
    fun insertAll(vararg cities:City)
}