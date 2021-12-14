package com.example.timetopray.ui.data.repository

import android.util.Log
import com.example.timetopray.ui.data.api.fridaymessage.FridayRetrofit
import com.example.timetopray.ui.data.api.praytime.Retrofit
import com.example.timetopray.ui.data.models.ayats.Ayat
import com.example.timetopray.ui.data.models.ayats.Ayats
import com.example.timetopray.ui.data.models.cities.Cities
import com.example.timetopray.ui.data.models.cities.City
import com.example.timetopray.ui.data.models.cities.Country
import com.example.timetopray.ui.data.models.fridaymessages.FridayMessageItem
import com.example.timetopray.ui.data.models.fridaymessages.FridayMessages
import com.example.timetopray.ui.data.models.praytimes.PrayTimes
import com.example.timetopray.ui.data.models.praytimes.PrayerTime
import com.example.timetopray.ui.data.models.userlocation.UserLocation
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

    fun getAllAyats(onResponse: (Ayats) -> Unit) {
        Retrofit.retrofit.let {
            Retrofit.api.getAllAyats().enqueue(object : Callback<Ayats> {
                override fun onResponse(call: Call<Ayats>, response: Response<Ayats>) {
                    response.body()?.let {
                        onResponse(it)
                    }
                }

                override fun onFailure(call: Call<Ayats>, t: Throwable) {
                    Log.d("Error", "Failed To Receive Data")
                }
            })
        }
    }

    fun getAllMessages(onResponse: (FridayMessages) -> Unit) {
        FridayRetrofit.retrofit.let {
            FridayRetrofit.api.getAllMessages().enqueue(object : Callback<FridayMessages> {
                override fun onResponse(
                    call: Call<FridayMessages>,
                    response: Response<FridayMessages>
                ) {
                    response.body()?.let {
                        onResponse(it)
                    }
                }

                override fun onFailure(call: Call<FridayMessages>, t: Throwable) {
                    Log.d("Error", "Failed To Receive Data")
                }

            })
        }
    }

    val getCity = timeToPrayDao.getCity()

    val getAllTimes = timeToPrayDao.getAllTimes()

    val getAllAyats = timeToPrayDao.getAllAyats()

    val getAllFridayMessages = timeToPrayDao.getAllFridayMessages()

    val getUserLocation = timeToPrayDao.getUserLocation()

    suspend fun insertCity(city: City) {
        timeToPrayDao.insertCity(city)
    }

    suspend fun insertTime(prayTime: PrayerTime) {
        timeToPrayDao.insertTime(prayTime)
    }

    suspend fun insertAyat(ayat: Ayat){
        timeToPrayDao.insertAyat(ayat)
    }

    suspend fun insertFridayMessage(fridayMessageItem: FridayMessageItem) {
        timeToPrayDao.insertFridayMessage(fridayMessageItem)
    }

    suspend fun insertUserLocation(userLocation: UserLocation) {
        timeToPrayDao.insertUserLocation(userLocation)
    }

    suspend fun deleteAllCityInfo() {
        timeToPrayDao.deleteAllCityInfo()
    }

    suspend fun deleteAllTimes() {
        timeToPrayDao.deleteAllTimes()
    }

    suspend fun deleteUserLocation() {
        timeToPrayDao.deleteUserLocation()
    }
}