package com.example.timetopray.ui.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.timetopray.ui.data.api.Retrofit
import com.example.timetopray.ui.data.models.cities.Cities
import com.example.timetopray.ui.data.models.cities.City
import com.example.timetopray.ui.data.models.cities.Country
import com.example.timetopray.ui.data.models.praytimes.PrayTimes
import com.example.timetopray.ui.data.models.praytimes.PrayerTime
import com.example.timetopray.ui.data.room.TimeToPrayDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val timeToPrayDao: TimeToPrayDao) {

    fun getAllCities(onResponse: (Country) -> Unit) {
        Retrofit.retrofit.let {
            Retrofit.api.getAllCities().enqueue(object : Callback<Cities> {
                override fun onResponse(call: Call<Cities>, response: Response<Cities>) {
                    response.body()?.let { responseBody ->
                        val country = responseBody.countries.filter {
                            it.name == "TÜRKİYE"
                        }
                        onResponse(country[0])
                    }
                }

                override fun onFailure(call: Call<Cities>, t: Throwable) {
                    Log.d("Error", "Failed To Receive Data")
                }
            })
        }
    }

    fun getTimes(cityId: String, onResponse: (PrayTimes) -> Unit) {
        Retrofit.retrofit.let {
            Retrofit.api.getTimes(cityId).enqueue(object : Callback<PrayTimes> {
                override fun onResponse(call: Call<PrayTimes>, response: Response<PrayTimes>) {
                    response.body()?.let { responseBody ->
                        onResponse(responseBody)
                    }
                }

                override fun onFailure(call: Call<PrayTimes>, t: Throwable) {
                    Log.d("Error", "Failed To Receive Data")
                }
            })
        }
    }

    val getCity = timeToPrayDao.getCity()

    fun getAllTimes(): LiveData<List<PrayerTime>>? {
        return timeToPrayDao.getAllTimes()
    }

    suspend fun insertCity(city: City) {
        timeToPrayDao.insertCity(city)
    }

    suspend fun insertTime(prayTime: PrayerTime) {
        timeToPrayDao.insertTime(prayTime)
    }

    suspend fun deleteAllCityInfo() {
        timeToPrayDao.deleteAllCityInfo()
    }

    suspend fun deleteAllTimes() {
        timeToPrayDao.deleteAllTimes()
    }
}