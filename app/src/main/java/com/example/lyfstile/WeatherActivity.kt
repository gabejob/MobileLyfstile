package com.example.lyfstile

import android.content.Intent
import android.graphics.Bitmap
import android.location.LocationManager
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


class WeatherActivity : AppCompatActivity(), View.OnClickListener, PassData,
    ActionbarFragment.ClickInterface {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private var longitude = -111.845205
    private var latitude = 40.767778
    private val appID = "241f90adea0d5886a14c0dcfd83b5187"
    private val url =
        "https://api.openweathermap.org/data/2.5/onecall?lat=${latitude}&lon=${longitude}&exclude=minutely,hourly,alerts,daily&appid=${appID}&units=imperial"
    private var user: User? = null
    private var profilePic: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        user = extras?.get(USER_DATA) as User
        profilePic = intent.getParcelableExtra(PROFILE_PIC)
        setContentView(R.layout.activity_weather)

        val actionbarFragment = ActionbarFragment()
        val fragtrans = supportFragmentManager.beginTransaction()
        fragtrans.replace(R.id.action_bar_fragment, actionbarFragment, ACTION_BAR)
        fragtrans.commit()
        actionbarFragment.bindClickInterface(this)

        try {
            val weather = getWeather()

            findViewById<TextView>(R.id.WeatherStatus)?.text =
                "Today's weather: ${weather.current.weather.first().main}"
            findViewById<TextView>(R.id.Temperature)?.text = "Temperature: ${weather.current.temp} °F"
            findViewById<TextView>(R.id.TemperatureFeelsLike)?.text =
                "Feels like: ${weather.current.feels_like} °F"
            findViewById<TextView>(R.id.Humidity)?.text = "Humidity: ${weather.current.humidity} %"

            when (weather.current.weather.first().main) {
                "Clear" -> findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.clear)
                "Rain" -> findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.rain)
                "Clouds" -> findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.clouds)
                "Snow" -> findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.snow)
                else -> findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.clouds)
            }
        } catch (e: Exception) {
            val weather = getWeatherNoGust()

            findViewById<TextView>(R.id.WeatherStatus)?.text =
                "Today's weather: ${weather.current.weather.first().main}"
            findViewById<TextView>(R.id.Temperature)?.text = "Temperature: ${weather.current.temp} °F"
            findViewById<TextView>(R.id.TemperatureFeelsLike)?.text =
                "Feels like: ${weather.current.feels_like} °F"
            findViewById<TextView>(R.id.Humidity)?.text = "Humidity: ${weather.current.humidity} %"

            when (weather.current.weather.first().main) {
                "Clear" -> findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.clear)
                "Rain" -> findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.rain)
                "Clouds" -> findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.clouds)
                "Snow" -> findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.snow)
                else -> findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.clouds)
            }
        }

    }

    override fun onClick(view: View) {
    }

    override fun onDataPass(data: Data) {
    }

    override fun actionButtonClicked(id: Int) {
        val temp = User()
        temp.tempConstruct()
        when (id) {
            R.id.health -> {
                val healthScreen = Intent(this, HealthActivity::class.java)
                healthScreen.putExtra(USER_DATA, user)
                healthScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(healthScreen)
            }
            R.id.hiker -> {
                val mapScreen = Intent(this, MapActivity::class.java)
                mapScreen.putExtra(USER_DATA, user)
                mapScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(mapScreen)
            }
            R.id.home -> {
                val homeScreen = Intent(this, HomeScreen::class.java)
                homeScreen.putExtra(USER_DATA, user)
                homeScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(homeScreen)
            }
            R.id.settings ->
            {
                val settingsScreen = Intent(this, SettingsActivity::class.java)
                settingsScreen.putExtra(USER_DATA, user)
                settingsScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(settingsScreen)
            }
        }
    }

    private fun getWeather(): WeatherInfo {
        val policy = ThreadPolicy.Builder().permitAll().build()
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
                return mapper.readValue<WeatherInfo>(json)
            }
        }
    }

    private fun getWeatherNoGust(): WeatherInfoNoGust {
        val policy = ThreadPolicy.Builder().permitAll().build()
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
                return mapper.readValue<WeatherInfoNoGust>(json)
            }
        }
    }
}