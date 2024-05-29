package com.example.bump.Controller.LocationHandlers
import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdate(intervalL: Long): Flow<Location>
    class LocationException(message: String): Exception()
}