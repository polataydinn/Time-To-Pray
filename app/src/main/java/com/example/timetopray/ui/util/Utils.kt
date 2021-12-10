package com.example.timetopray.ui.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import androidx.lifecycle.LiveData
import java.util.*

object Utils {

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context, onResult: (String) -> Unit) {
        var cityName: String?
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location =
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 5000, 0f,
                LocationListener {
                    val geoCoder: Geocoder? = Geocoder(context, Locale.getDefault())
                    cityName = it.let { mLocation ->
                        geoCoder?.getFromLocation(
                            mLocation.latitude,
                            mLocation.longitude,
                            1
                        )?.first()?.adminArea
                    }
                    cityName = cityName?.uppercase()
                    cityName?.let { mCityName -> onResult(mCityName) }
                })
    }

    @SuppressLint("MissingPermission")
    fun getDetailedLocation(context: Context, onResult: (String) -> Unit){
        var detailedName: String?
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location =
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 5000, 0f,
                LocationListener {
                    val geoCoder: Geocoder? = Geocoder(context, Locale.getDefault())
                    detailedName = it.let { mLocation ->
                        geoCoder?.getFromLocation(
                            mLocation.latitude,
                            mLocation.longitude,
                            1
                        )?.first()?.subAdminArea
                    }
                    detailedName?.let { mCityName -> onResult(mCityName) }
                })
    }

}