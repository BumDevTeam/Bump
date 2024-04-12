package com.example.bump

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.bump.Controller.Services.LocationService
import com.example.bump.View.MapActivity
import com.example.bump.View.theme.BlackTransparent
import com.example.bump.View.theme.BumpTheme

class MainActivity : ComponentActivity(){


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {

        //var mapActivity = MapActivity()

        //mapActivity.startActivity(Intent(this, MapActivity::class.java))

       // val intent = Intent(this, MapActivity::class.java)


        super.onCreate(savedInstanceState)

        //  Intent for creating map activity





        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE_LOCATION
            ),
            0
        )
        setContent {

            BumpTheme {


                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(BlackTransparent),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize(0.5f)
                                .background(Color.White),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center


                        ) {
                            Text(text = "Bump",
                                fontSize = 30.sp)

                            Spacer(modifier = Modifier.height(50.dp))

                            Button(onClick = {
                                finish()
                            }) {
                                Text(text = "Start")
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = {
                                Intent(applicationContext, LocationService::class.java).apply{
                                    action = LocationService.ACTION_STOP
                                    startService(this)
                                }
                            }) {
                                Text(text = "Stop")
                            }
                        }
                    }







            }


        }
    }

}
