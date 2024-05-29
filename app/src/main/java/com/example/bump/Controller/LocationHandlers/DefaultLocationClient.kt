package com.example.bump.Controller.LocationHandlers

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.example.bump.Controller.hasLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultLocationClient(
    private val context: Context,
    private val client: FusedLocationProviderClient
): LocationClient {
    @SuppressLint("MissingPermission")
    override fun getLocationUpdate(intervalL: Long): Flow<Location> {
        return callbackFlow {
            if(!context.hasLocationPermission())
            {
                throw LocationClient.LocationException("No permission for location!")
            }
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if(!isGpsEnabled && !isNetEnabled)
                throw LocationClient.LocationException("GPS is disabled!")

            val request = com.google.android.gms.location.LocationRequest.create().apply {
                priority = Priority.PRIORITY_HIGH_ACCURACY
                interval = intervalL // 0.5 seconds
                fastestInterval = 200L // 0.2 seconds
                smallestDisplacement = 0.1f // 0.1 meter
            }

            val locationCallback = object: LocationCallback()
            {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let {
                        location -> launch { send(location) }
                    }
                }
            }

            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose{
                client.removeLocationUpdates(locationCallback)
            }
        }

    }
}