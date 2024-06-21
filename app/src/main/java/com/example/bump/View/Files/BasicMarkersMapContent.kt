package com.example.bump.View.Files

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMapComposable

@Composable
@GoogleMapComposable
fun BasicMarkersMapContent(markersList: ArrayList<LatLng?>) {

    markersList.forEach {
       marker -> MyMarker(markerPosition = marker!!)
    }

}