package com.example.bump.View.Files

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MyMarker(markerPosition: LatLng) {
    Marker(
        state = MarkerState(position = markerPosition),
        title = "You are here",
        snippet = markerPosition.latitude.toString()+ " " + markerPosition.longitude.toString(),
        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
    )
}