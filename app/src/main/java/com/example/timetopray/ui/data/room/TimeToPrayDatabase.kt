package com.example.timetopray.ui.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.timetopray.ui.data.models.cities.City
import com.example.timetopray.ui.data.models.praytimes.PrayerTime

@Database(entities = [PrayerTime::class, City::class], version = 1, exportSchema = false)
abstract class TimeToPrayDatabase : RoomDatabase() {

    abstract fun timeToPrayDao(): TimeToPrayDao

    companion object {

        @Volatile
        private var INSTANCE: TimeToPrayDatabase? = null

        fun getDatabase(context: Context): TimeToPrayDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TimeToPrayDatabase::class.java,
                    "time_to_pray_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}