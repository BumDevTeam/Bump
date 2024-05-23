package com.example.bump.View

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationApp: Application() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(
                "location",
                "Location",
                NotificationManager.IMPORTANCE_LOW
            )
            val notManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notManager.createNotificationChannel(channel)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Request location updates with high accuracy
        requestLocationUpdates()
    }

    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .build()

        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, object : com.google.android.gms.location.LocationCallback() {
                override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                    // Handle location result
                    for (location in locationResult.locations) {
                        // Process the location object
                        // For example, log the latitude and longitude
                        println("Location: ${location.latitude}, ${location.longitude}")
                    }
                }
            }, mainLooper)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}