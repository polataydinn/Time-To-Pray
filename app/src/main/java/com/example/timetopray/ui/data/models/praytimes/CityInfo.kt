package com.example.timetopray.ui.data.models.praytimes


import com.google.gson.annotations.SerializedName

data class CityInfo(
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String,
    @SerializedName("name")
    val name: String
)