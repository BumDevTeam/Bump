package com.example.bump.View.Files

import android.Manifest
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.bump.Controller.Services.LocationService
import com.example.bump.MainActivity
import com.example.bump.View.MapActivity
import com.example.bump.View.theme.BlackTransparent

@Composable
fun HomeScreen(mainActivity: MainActivity)
{

    Column(modifier = Modifier
        .fillMaxSize()
        .background(BlackTransparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Column(
            modifier = Modifier
                .fillMaxHeight(0.35f)
                .fillMaxWidth(0.5f)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Bump",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            startButton(mainActivity = mainActivity)

        }
    }
}

@Composable
fun startButton(mainActivity: MainActivity)
{
    Button(onClick = {
        ActivityCompat.requestPermissions(
            mainActivity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE_LOCATION
            ),
            0
        )

        mainActivity.finish()
    }) {
        Text(text = "Start")
    }
}
