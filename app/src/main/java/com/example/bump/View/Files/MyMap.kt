package com.example.bump.View.Files

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap

@Composable
fun MyMap(position: LatLng, camPos: CameraPositionState)
{
    MyCamera(pos = position, camPos)

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = camPos

    )
    {
        MyMarker(markerPosition = position)

    }
}