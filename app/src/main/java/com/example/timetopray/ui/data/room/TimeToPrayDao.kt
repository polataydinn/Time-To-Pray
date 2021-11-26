package com.example.timetopray.ui.data.room

import androidx.room.*
import com.example.timetopray.ui.data.models.cities.City
import com.example.timetopray.ui.data.models.praytimes.PrayerTime

@Dao
interface TimeToPrayDao {

    @Query("SELECT * FROM city_table")
    fun getCity(): City

    @Query("SELECT * FROM time_table")
    fun getAllTimes(): List<PrayerTime>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCity(city: City)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTime(prayTime: PrayerTime)

    @Query("DELETE FROM city_table")
    suspend fun deleteAllCityInfo()

    @Query("DELETE FROM time_table")
    suspend fun deleteAllTimes()

}