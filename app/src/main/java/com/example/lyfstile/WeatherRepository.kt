package com.example.lyfstile

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.StrictMode
import androidx.lifecycle.LiveData
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherRepository  : LocationListener {
    private var longitude = 0.0
    private var latitude = 0.0

    private val appID = "241f90adea0d5886a14c0dcfd83b5187"
    private val url =
        "https://api.openweathermap.org/data/2.5/onecall?lat=${latitude}&lon=${longitude}&exclude=minutely,hourly,alerts,daily&appid=${appID}&units=imperial"

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
    }

    fun getWeather(): WeatherInfo {
        return readWeather("wind") as WeatherInfo
    }

    fun getWeatherNoGust(): WeatherInfoNoGust {
        return readWeather("noWind") as WeatherInfoNoGust
    }

    fun readWeather(type: String): Any {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val mURL = URL(url)
        with(mURL.openConnection() as HttpURLConnection) {
            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                val mapper = jacksonObjectMapper()
                val json = response.toString()

                return when (type) {
                    "wind" -> mapper.readValue<WeatherInfo>(json)
                    "noWind" -> mapper.readValue<WeatherInfoNoGust>(json)
                    else -> error("Unable to get weather info")
                }
            }
        }
    }
}