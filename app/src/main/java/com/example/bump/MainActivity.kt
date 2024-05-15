package com.example.bump

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.bump.View.Files.HomeScreen
import com.example.bump.View.theme.BumpTheme

class MainActivity : ComponentActivity(){



    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)

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
                HomeScreen(mainActivity = this)
            }

        }
    }


    // With this fun user cant go inside mapActivity with using back button
    @Override
    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this, "Click start", Toast.LENGTH_SHORT).show()
    }

}
