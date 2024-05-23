package com.example.bump.View.Files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MyCamera(pos: LatLng, cameraPositionState: CameraPositionState)
{
    LaunchedEffect(pos) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(pos, 40f)
        )
    }
}