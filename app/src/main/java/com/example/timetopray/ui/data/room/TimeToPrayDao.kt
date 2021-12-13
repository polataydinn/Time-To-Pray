package com.example.timetopray.ui.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.timetopray.ui.data.models.cities.City
import com.example.timetopray.ui.data.models.fridaymessages.FridayMessageItem
import com.example.timetopray.ui.data.models.fridaymessages.FridayMessages
import com.example.timetopray.ui.data.models.praytimes.PrayerTime
import com.example.timetopray.ui.data.models.userlocation.UserLocation

@Dao
interface TimeToPrayDao {

    @Query("SELECT * FROM city_table")
    fun getCity(): LiveData<City>?

    @Query("SELECT * FROM time_table")
    fun getAllTimes(): LiveData<List<PrayerTime>>?

    @Query("SELECT * FROM friday_messages")
    fun getAllFridayMessages(): LiveData<List<FridayMessageItem>>

    @Query("SELECT * FROM user_location")
    fun getUserLocation(): LiveData<UserLocation>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCity(city: City)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTime(prayTime: PrayerTime)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFridayMessage(fridayMessageItem: FridayMessageItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserLocation(userLocation: UserLocation)

    @Query("DELETE FROM city_table")
    suspend fun deleteAllCityInfo()

    @Query("DELETE FROM time_table")
    suspend fun deleteAllTimes()

    @Query("DELETE FROM user_location")
    suspend fun deleteUserLocation()

}