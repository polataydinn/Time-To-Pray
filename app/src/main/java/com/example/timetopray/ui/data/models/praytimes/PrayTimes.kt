package com.example.timetopray.ui.data.models.praytimes


import com.google.gson.annotations.SerializedName

data class PrayTimes(
    @SerializedName("CityInfo")
    val cityInfo: CityInfo,
    @SerializedName("CountryInfo")
    val countryInfo: CountryInfo,
    @SerializedName("Info")
    val info: Info,
    @SerializedName("PrayerTimes")
    val prayerTimes: List<PrayerTime>
)