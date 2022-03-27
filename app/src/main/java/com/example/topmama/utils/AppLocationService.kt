package com.example.topmama.utils

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
open class AppLocationService(context: Context) : Service(),
LocationListener {
   private var locationManager: LocationManager? =
   context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
   private lateinit var location: Location
   @SuppressLint("MissingPermission")
   fun getLocation(provider: String?): Location? {
      if (provider?.let { locationManager!!.isProviderEnabled(it) } == true) {
         if (provider != null) {
            locationManager!!.requestLocationUpdates(
               provider,
               MIN_TIME_FOR_UPDATE,
               MIN_DISTANCE_FOR_UPDATE.toFloat(), this
            )
         }
         if (locationManager != null) {
            location = provider?.let { locationManager!!.getLastKnownLocation(it) }!!
            return location
         }
      }
      return null
   }
   override fun onLocationChanged(location: Location) {}
   override fun onProviderDisabled(provider: String) {}
   override fun onProviderEnabled(provider: String) {}
   override fun onStatusChanged(
   provider: String,
   status: Int,
   extras: Bundle
   ) {
   }
   override fun onBind(arg0: Intent): IBinder? {
      return null
   }
   companion object {
      private const val MIN_DISTANCE_FOR_UPDATE: Long = 10
      private const val MIN_TIME_FOR_UPDATE = 1000 * 60 * 2.toLong()
   }
}