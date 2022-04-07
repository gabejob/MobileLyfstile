package com.example.lyfstile

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.os.StrictMode
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFrag : Fragment(), ActionbarFragment.ClickInterface, LocationListener {
    lateinit var viewModel: LyfViewModel
    private var longitude = 0.0
    private var latitude = 0.0
    private val appID = "241f90adea0d5886a14c0dcfd83b5187"
    private val url =
        "https://api.openweathermap.org/data/2.5/onecall?lat=${latitude}&lon=${longitude}&exclude=minutely,hourly,alerts,daily&appid=${appID}&units=imperial"


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

        val actionbarFragment = ActionbarFragment()

        val fragtrans = childFragmentManager.beginTransaction()
        fragtrans.replace(R.id.action_bar_fragment, actionbarFragment, ACTION_BAR)
        fragtrans.commit()
        actionbarFragment.bindClickInterface(this)

        try {
            val weather = getWeather()

            view.findViewById<TextView>(R.id.WeatherStatus)?.text =
                "Today's weather: ${weather.current.weather.first().main}"
            view.findViewById<TextView>(R.id.Temperature)?.text = "Temperature: ${weather.current.temp} °F"
            view.findViewById<TextView>(R.id.TemperatureFeelsLike)?.text =
                "Feels like: ${weather.current.feels_like} °F"
            view.findViewById<TextView>(R.id.Humidity)?.text = "Humidity: ${weather.current.humidity} %"

            when (weather.current.weather.first().main) {
                "Clear" -> view.findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.clear)
                "Rain" -> view.findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.rain)
                "Clouds" -> view.findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.clouds)
                "Snow" -> view.findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.snow)
                else -> view.findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.clouds)
            }
        } catch (e: Exception) {
            val weather = getWeatherNoGust()

            view.findViewById<TextView>(R.id.WeatherStatus)?.text =
                "Today's weather: ${weather.current.weather.first().main}"
            view.findViewById<TextView>(R.id.Temperature)?.text = "Temperature: ${weather.current.temp} °F"
            view.findViewById<TextView>(R.id.TemperatureFeelsLike)?.text =
                "Feels like: ${weather.current.feels_like} °F"
            view.findViewById<TextView>(R.id.Humidity)?.text = "Humidity: ${weather.current.humidity} %"

            when (weather.current.weather.first().main) {
                "Clear" -> view.findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.clear)
                "Rain" -> view.findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.rain)
                "Clouds" -> view.findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.clouds)
                "Snow" -> view.findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.snow)
                else -> view.findViewById<ImageView>(R.id.weather_image).setImageResource(R.drawable.clouds)
            }
        }
        return view
    }

    override fun actionButtonClicked(id: Int) {
        when (id) {
            R.id.health -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_weatherFrag_to_healthFrag)
                }
            }
            R.id.hiker -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_weatherFrag_to_mapsFrag)
                }
            }
            R.id.settings -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_weatherFrag_to_settingsFrag)
                }
            }
            R.id.home -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_weatherFrag_to_homeScreenFrag)
                }
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
    }

    private fun getWeather(): WeatherInfo {
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
                return mapper.readValue<WeatherInfo>(json)
            }
        }
    }

    private fun getWeatherNoGust(): WeatherInfoNoGust {
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
                return mapper.readValue<WeatherInfoNoGust>(json)
            }
        }
    }
}