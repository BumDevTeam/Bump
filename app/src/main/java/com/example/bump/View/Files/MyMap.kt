package com.example.bump.View.Files

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType

@Composable
fun MyMap(position: LatLng, camPos: CameraPositionState, markersArrayList: List<LatLng?>)
{
    MyCamera(pos = position, camPos)


    var properties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.NORMAL
            )
        )
    }

    GoogleMap(
        properties = properties,
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = camPos

    )
    {
//        MyMarker(markerPosition = position)

        BasicMarkersMapContent(markersList = markersArrayList)

//        for(i in markersArrayList.indices)
//        {
//            MyMarker(markerPosition = markersArrayList[i]!!)
//        }

//        markersArrayList.forEach {
//            position -> mutableStateOf( MyMarker(markerPosition = position!!))
//        }

    }
}