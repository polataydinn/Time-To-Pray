package com.example.timetopray.ui.data.models.praytimes


import com.google.gson.annotations.SerializedName

data class CountryInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)