package com.example.timetopray.ui.data.api

import com.example.timetopray.ui.data.models.cities.Cities
import com.example.timetopray.ui.data.models.praytimes.PrayTimes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PrayTimeApi {
    @GET("getAllCities")
    fun getAllCities(): Call<Cities>

    @GET("getTimes/{cityId}")
    fun getTimes(@Path(value = "cityId", encoded = true) cityId: String): Call<PrayTimes>
}