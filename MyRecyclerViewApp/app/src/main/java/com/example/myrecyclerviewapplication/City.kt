package com.example.myrecyclerviewapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey(autoGenerate = true)
    val cityID:Long = 0,
    @ColumnInfo(name="city_name") var name:String,
    @ColumnInfo(name="population") var population:Int,
    @ColumnInfo(name="isCapital")var isCapital:Boolean = false)