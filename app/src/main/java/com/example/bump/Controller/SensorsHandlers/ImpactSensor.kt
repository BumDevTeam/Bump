package com.example.bump.Controller.SensorsHandlers

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class ImpactSensor(
    context: Context
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_GYROSCOPE,
    sensorType = Sensor.TYPE_GYROSCOPE
) {


}