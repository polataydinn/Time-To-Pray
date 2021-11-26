package com.example.timetopray.ui.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetopray.ui.data.models.cities.City
import com.example.timetopray.ui.data.models.praytimes.PrayerTime
import com.example.timetopray.ui.data.repository.Repository
import com.example.timetopray.ui.data.room.TimeToPrayDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimeToPrayViewModel(application: Application): AndroidViewModel(application) {

    private val timeToPrayDao = TimeToPrayDatabase.getDatabase(application).timeToPrayDao()
    private val repository = Repository(timeToPrayDao)
    //val getCity = repository.getCity
  //  val getAllTimes = repository.getAllTimes()

    fun getAllCities(cityName: String){
        var city = listOf<City>()
        repository.getAllCities { cities ->
            cities.countries.forEach { country ->
                city = country.cities.filter {
                    it.name == cityName
                }
            }
            println("breakpoint")

        }
    }

    fun getTimes(cityId: String){
        repository.getTimes(cityId){prayTimes ->
            prayTimes.prayerTimes.forEach {
                insertTime(it)
            }
        }
    }

    fun insertCity(city: City){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCity(city)
        }
    }

    fun insertTime(prayTime: PrayerTime){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTime(prayTime)
        }
    }

    fun deleteAllCityInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCityInfo()
        }
    }

    fun deleteAllTimes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTimes()
        }
    }
}