package com.example.bump.Controller.APIController

import android.location.Location
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.json.JSONObject
import java.util.concurrent.TimeoutException
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*


class Api {


     suspend fun getAll():String
    {

        val  client = HttpClient(CIO)

        val response: HttpResponse = client.get("https://bumpserver.fly.dev/api/entry")
        val values: String = response.body()
        Log.d("GET ALL response",values)
        client.close()

        return ""
    }

     suspend fun getNearby(longitude:String, latitude:String):String
    {

        try {

            fun Application.module() {
                install(ContentNegotiation) {
                    json()
                }
            }

            val  client = HttpClient(CIO)



            val rootObject= JSONObject()
            rootObject.put("longitude",longitude)
            rootObject.put("latitude",latitude)

            val response: HttpResponse = client.post("https://bumpserver.fly.dev/api/entry/getNearby") {
                setBody(rootObject) //TODO JSON BODY FIX
                contentType(ContentType.Application.Json)
            }
            val values: String = response.body()
            Log.d("GET NEARBY response",values)
            Log.d("JSON BODY:",rootObject.toString())
            client.close()



        } catch (e: ClientRequestException ) {
            Log.d("ERROR!!!", "ClientRequestException ${e.message}")
        } catch (e:ServerResponseException ) {
            Log.d("ERROR!!!", "ServerResponseException ${e.message}")
        } catch (e: TimeoutException) {
            Log.d("ERROR!!!", "TimeoutException ${e.message}")
        } catch (e: Exception) {
            Log.d("ERROR!!!", "Exception ${e.message}")
        }





        return ""
    }

     suspend fun setLocation(longitude:String, latitude:String, value:String)
    {
        val  client = HttpClient(CIO)

        val body = ""

        val response: HttpResponse = client.post("https://bumpserver.fly.dev/api/entry/add") {
            setBody(body)
        }
        val values: String = response.body()
        Log.d("response",values)
        client.close()
    }

}