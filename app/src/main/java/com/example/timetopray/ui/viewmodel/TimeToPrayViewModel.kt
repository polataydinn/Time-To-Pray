package com.example.timetopray.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetopray.ui.data.models.ayats.Ayat
import com.example.timetopray.ui.data.models.cities.City
import com.example.timetopray.ui.data.models.fridaymessages.FridayMessageItem
import com.example.timetopray.ui.data.models.praytimes.PrayerTime
import com.example.timetopray.ui.data.models.userlocation.UserLocation
import com.example.timetopray.ui.data.repository.Repository
import com.example.timetopray.ui.data.room.TimeToPrayDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimeToPrayViewModel(application: Application) : AndroidViewModel(application) {

    private val timeToPrayDao = TimeToPrayDatabase.getDatabase(application).timeToPrayDao()
    private val repository = Repository(timeToPrayDao)
    val getCity = repository.getCity
    val getAllTimes = repository.getAllTimes
    val getAllAyats = repository.getAllAyats
    val getUserLocation = repository.getUserLocation
    val getAllFridayMessages = repository.getAllFridayMessages

    fun getAllCities(cityName: String) {
        var city = listOf<City>()
        repository.getAllCities { country ->
            city = country.cities.filter {
                it.name == cityName
            }
            if (city.isNotEmpty()) {
                city[0].let {
                    insertCity(it)
                    getTimes(it._id)
                }
            }
        }
    }

    fun getTimes(cityId: String) {
        repository.getTimes(cityId) { prayTimes ->
            prayTimes.prayerTimes.forEach {
                insertTime(it)
            }
        }
    }

    fun getAllAyats(){
        repository.getAllAyats {
            it.ayats?.forEach { ayat ->
                ayat?.let {
                    insertAyat(ayat)
                }
            }
        }
    }


    fun getAllMessages() {
        repository.getAllMessages {
            it.fridayMessage?.forEach { fridayMessageItem ->
                fridayMessageItem?.let { mFridayMessageItem ->
                    insertFridayMessage(mFridayMessageItem)
                }
            }
        }
    }

    fun insertCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCity(city)
        }
    }

    fun insertTime(prayTime: PrayerTime) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTime(prayTime)
        }
    }

    fun insertAyat(ayat: Ayat){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAyat(ayat)
        }
    }

    fun insertFridayMessage(fridayMessageItem: FridayMessageItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFridayMessage(fridayMessageItem)
        }
    }

    fun insertUserLocation(userLocation: UserLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUserLocation(userLocation)
        }
    }

    fun deleteAllCityInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCityInfo()
        }
    }

    fun deleteAllTimes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTimes()
        }
    }

    fun deleteUserLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserLocation()
        }
    }


}