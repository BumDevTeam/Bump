package com.example.bump.View

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.bump.Controller.Services.LocationService
import com.example.bump.MainActivity
import com.example.bump.R
import com.example.bump.View.Files.MyMap
import com.example.bump.View.Files.MyMarker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


class MapActivity: FragmentActivity(),  SensorEventListener {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private var mSensorManager : SensorManager?= null
    private var mAccelerometer : Sensor?= null

    private lateinit var googleMap: GoogleMap


    var mCenterMarker: Marker? = null


    private var x = mutableStateOf(0.0)
    private var y = mutableStateOf(0.0)

    private var camPos = mutableStateOf(CameraPosition.fromLatLngZoom(LatLng(x.value,y.value),10f))




    private var userLocationMarker: Marker? = null

    private lateinit var mService: LocationService
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService().  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            val binder = service as LocationService.LocationBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {



        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // focus in accelerometer
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        // setup the window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            // Permissions already granted, proceed with location updates
            val application = LocationApp()
        }


//        setContentView(R.layout.mapfragment)

        setContent {
//            val cameraPositionState = rememberCameraPositionState ()
//
//            LaunchedEffect(x.value, y.value) {
//                cameraPositionState.animate(
//                    update = CameraUpdateFactory.newLatLngZoom(LatLng(x.value, y.value), 40f)
//                )
//            }
//
//            GoogleMap(
//                modifier = Modifier.fillMaxSize(),
//                cameraPositionState = cameraPositionState
//
//            )
//            {
//                MyMarker(markerPosition = LatLng(x.value,y.value))
//
//            }


            val cameraPositionState = rememberCameraPositionState ()

            MyMap(position = LatLng(x.value, y.value), camPos = cameraPositionState)
        }

//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
//        mapFragment?.getMapAsync(this)

        val intent = Intent(this, MainActivity::class.java)




        startActivity(intent)
    }



    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if(mBound)
            {
                x.value = mService.getLocX()
                y.value = mService.getLocY()

                //userLocationMarker!!.position = LatLng(x,y)

                //setContent {
                    //MyMarker(markerPosition = LatLng(x,y))

                //}





            }
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this,mAccelerometer,
            SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

    override fun onStart() {
        super.onStart()
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE_LOCATION
            ),
            0
        )
        Intent(this, LocationService::class.java).apply{
            action = LocationService.ACTION_START
            startService(this)}
        Intent(this, LocationService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }


}