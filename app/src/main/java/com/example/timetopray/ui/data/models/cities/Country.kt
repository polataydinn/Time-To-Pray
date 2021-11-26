package com.example.timetopray.ui.data.models.cities


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("cities")
    val cities: List<City>,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)