package com.example.timetopray.ui.data.models.praytimes


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "time_table")
data class PrayerTime(
    @PrimaryKey
    val _id: Int,
    @SerializedName("aksam")
    val aksam: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("gunes")
    val gunes: String,
    @SerializedName("hDate")
    val hDate: String,
    @SerializedName("ikindi")
    val ikindi: String,
    @SerializedName("imsak")
    val imsak: String,
    @SerializedName("ogle")
    val ogle: String,
    @SerializedName("yatsi")
    val yatsi: String
)