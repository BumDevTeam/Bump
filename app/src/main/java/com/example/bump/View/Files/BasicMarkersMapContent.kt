package com.example.bump.View.Files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
@GoogleMapComposable
fun BasicMarkersMapContent(markersList: List<LatLng?>) {
    markersList.forEach { markerPosition ->
        markerPosition?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Marker at (${it.latitude}, ${it.longitude})"
            )
        }
    }

}