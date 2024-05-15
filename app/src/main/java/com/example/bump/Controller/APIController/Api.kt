package com.example.bump.Controller.APIController

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException


//          ____________usage______________

//val api = Api()
//
//        api.getAll { response ->
//            Log.i("NUHUH!", response)
//        }
//        api.addEntry("4.87167","4.654014","6.66")
//        {
//                response ->  Log.i("YEAH!", response)
//        }
//        api.getNearby("5.87167","5.654014")
//        {
//            response ->  Log.i("MICHAL!", response)
//        }


class Api {

    private val client = OkHttpClient()

    fun getAll(callback: (String) -> Unit) {
        var ret = ""
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
                    ret =  response.body!!.string()
                    callback(ret)
                }
            }
        })
    }

    fun getNearby(longitude: String, latitude:String, callback: (String) -> Unit)
    {
        var ret = ""
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
                // Handle failure
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        // Handle unsuccessful response - there will be not unsuccessful response
                        callback("")
                    } else {
                        // Handle successful response
                        ret =  response.body!!.string()
                        callback(ret)
                    }
                }
            }
        })
    }
    fun addEntry(longitude: String, latitude:String,value:String, callback: (String) -> Unit)
    {
        var ret = ""
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
                // Handle failure - only failure here is you
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        // Handle unsuccessful response - there will be not unsuccessful response
                        callback("")
                    } else {
                        // Handle successful response
                        ret =  response.body!!.string()
                        callback(ret)
                    }
                }
            }
        })
    }
}