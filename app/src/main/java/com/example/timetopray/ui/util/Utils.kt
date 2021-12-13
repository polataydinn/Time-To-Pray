package com.example.timetopray.ui.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.*

object Utils {

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context, onResult: (Address?) -> Unit) {
        var cityName: Address?
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ifPermissionsDenied(context)) {
            return
        } else {
            val location = locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 5000, 0f,
                LocationListener {
                    try {
                        val geoCoder: Geocoder = Geocoder(context, Locale.getDefault())
                        cityName = it.let { mLocation ->

                            geoCoder.getFromLocation(
                                mLocation.latitude,
                                mLocation.longitude,
                                1
                            )?.first()
                        }
                        cityName?.let { mCityName -> onResult(mCityName) }
                    } catch (ioException: IOException) {
                    }
                })

        }

    }

    private fun ifPermissionsDenied(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }
}