package com.example.timetopray.ui.data.models.cities


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("Countries")
    val countries: Int,
    @SerializedName("Status")
    val status: String
)