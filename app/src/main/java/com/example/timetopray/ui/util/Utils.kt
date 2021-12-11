package com.example.timetopray.ui.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.example.timetopray.ui.activities.MainActivity
import java.util.*

object Utils {

    fun getLocation(context: Context, onResult: (Address?) -> Unit) {
        var cityName: Address?
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            val location = locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 5000, 0f,
                LocationListener {
                    val geoCoder: Geocoder? = Geocoder(context, Locale.getDefault())
                    cityName = it.let { mLocation ->
                        geoCoder?.getFromLocation(
                            mLocation.latitude,
                            mLocation.longitude,
                            1
                        )?.first()
                    }
                    cityName?.let { mCityName -> onResult(mCityName) }
                })
        }

    }
}