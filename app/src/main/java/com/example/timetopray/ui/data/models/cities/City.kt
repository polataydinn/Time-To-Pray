package com.example.timetopray.ui.data.models.cities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city_table")
data class City(
    @PrimaryKey
    @SerializedName("id")
    val _id: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String,
    @SerializedName("name")
    val name: String
)