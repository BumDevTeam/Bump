package com.example.bump.View.Files

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bump.Controller.Services.LocationService
import com.example.bump.MainActivity

@Composable
fun HomeScreen(mainActivity: MainActivity)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(text = "Bump",
            fontSize = 30.sp)

        Spacer(modifier = Modifier.height(50.dp))

        startButton(mainActivity = mainActivity)

        Spacer(modifier = Modifier.height(16.dp))

        stopButton(mainActivity = mainActivity)
    }
}

@Composable
fun startButton(mainActivity: MainActivity)
{
    Button(onClick = {
        Intent(mainActivity.applicationContext, LocationService::class.java).apply{
            action = LocationService.ACTION_START
            mainActivity.startService(this)
        }
    }) {
        Text(text = "Start")
    }
}

@Composable
fun stopButton(mainActivity: MainActivity)
{
    Button(onClick = {
        Intent(mainActivity.applicationContext, LocationService::class.java).apply{
            action = LocationService.ACTION_STOP
            mainActivity.startService(this)
        }
    }) {
        Text(text = "Stop")
    }
}