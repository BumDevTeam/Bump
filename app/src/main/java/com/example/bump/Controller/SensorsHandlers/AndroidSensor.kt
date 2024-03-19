package com.example.bump.Controller.SensorsHandlers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

abstract class AndroidSensor(
    private val context: Context,
    private val sensorFeature: String,
    sensorType: Int
): MeasurableSensor(sensorType), SensorEventListener {
    override val doesExist: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun startListening() {
        if(!doesExist){
            return
        }
        if(::sensorManager.isInitialized && sensor == null)
        {
            sensorManager = context.getSystemService(SensorManager::class.java) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
        }
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)

        }
    }

    override fun stopListening() {
        if(!doesExist || !::sensorManager.isInitialized)
        {
            return
        }
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(!doesExist)
        {
            return
        }
        if(event?.sensor?.type == sensorType)
        {
            onSensorValuesChange?.invoke(event.values.toList())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}