package com.example.timetopray.ui.data.models.userlocation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_location")
data class UserLocation(
    @PrimaryKey
    val cityName: String,
    val detailedAddress: String
)
