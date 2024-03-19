package com.example.bump.Controller.SensorsHandlers

abstract class MeasurableSensor(
    protected val sensorType: Int
) {
    protected var onSensorValuesChange: ((List<Float>) -> Unit)? = null

    abstract val doesExist: Boolean
    abstract fun startListening()
    abstract fun stopListening()

    fun setOnSensorValuesChangedListener(listener: (List<Float>) -> Unit)
    {
        onSensorValuesChange = listener
    }
}