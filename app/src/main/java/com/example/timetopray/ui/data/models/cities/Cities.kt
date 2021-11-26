package com.example.timetopray.ui.data.models.cities

import com.google.gson.annotations.SerializedName


data class Cities(
    @SerializedName("Countries")
    val countries: List<Country>,
    @SerializedName("Info")
    val info: Info
)