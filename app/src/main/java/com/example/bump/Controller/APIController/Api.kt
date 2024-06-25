package com.example.bump.Controller.APIController

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.model.LatLng
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException

class Api {

    private val client = OkHttpClient()
    val gson = GsonBuilder()
        .registerTypeAdapter(LatLng::class.java, JsonDeserializer { json, _, _ ->
            val jsonObject = json.asJsonObject
            LatLng(
                jsonObject.get("latitude").asDouble,
                jsonObject.get("longitude").asDouble
            )
        })
        .create()

    fun getAll(callback: (String) -> Unit) {
        val request = Request.Builder()
            .url("https://bumpserver.fly.dev/api/entry")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback("")
            }

            override fun onResponse(call: Call, response: Response)   {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    callback(response.body!!.string())
                }
            }
        })
    }

    fun getNearby(longitude: String, latitude: String, callback: (String) -> Unit) {
        val json = """
        {
            "longitude": "$longitude",
            "latitude": "$latitude"
        }
        """.trimIndent()

        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("https://bumpserver.fly.dev/api/entry/getNearby")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        callback(response.body!!.string())
                    } else {
                        callback("")
                    }
                }
            }
        })
    }

    fun addEntry(longitude: String, latitude: String, value: String, callback: (String) -> Unit) {
        val json = """
        {
            "longitude": "$longitude",
            "latitude": "$latitude",
            "value": "$value"
        }
        """.trimIndent()

        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("https://bumpserver.fly.dev/api/entry/add")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        callback(response.body!!.string())
                    } else {
                        callback("")
                    }
                }
            }
        })
    }

    fun returnParsed(x: Double, y: Double, list: MutableState<List<LatLng?>>) {
        this.getNearby(x.toString(), y.toString()) { response ->
            if (response.isNotEmpty()) {
                // Log the response to ensure it contains expected data
                Log.d("ApiResponse", "Response: $response")

                // Parse the response into a List of LatLng
                val latLngListType = object : TypeToken<List<LatLng?>>() {}.type
                val parsedList: List<LatLng?> = gson.fromJson(response, latLngListType)

                // Log the parsed list to verify contents
                Log.d("ParsedList", "Parsed List: $parsedList")

                // Update the mutable state with the parsed list
                list.value = parsedList
            }
        }
    }
}
