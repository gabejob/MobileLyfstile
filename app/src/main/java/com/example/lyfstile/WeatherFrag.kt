package com.example.lyfstile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import java.lang.System.currentTimeMillis
import java.lang.Thread.sleep
import java.sql.Date
import java.sql.Timestamp


/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFrag : Fragment(), ActionbarFragment.ClickInterface {
    lateinit var viewModel: LyfViewModel
    lateinit var weathers : String
    private val weatherRepository = WeatherRepository()

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

            val stamp = Timestamp(currentTimeMillis())
            val date = Date(stamp.time)

            sleep(1000)
            viewModel.getWeather(requireActivity(),date.toString())!!.observe(viewLifecycleOwner) {
                if (it != null) {
                    print(it)
                }
            }

            if (weathers == null) {
                val weather = weatherRepository.getWeather()
                setWeather(view, weather)
            }
            else
            {
                setWeather(view, WeatherInfo("","","","",Current(date.toString(),
                    "","",weathers,"","","","","","","","",
                "",mutableListOf<Weather>())))
            }

        } catch (e: Exception) {
            val weather = weatherRepository.getWeatherNoGust()
            setWeatherNoGust(view, weather)
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

    @SuppressLint("SetTextI18n")
    fun setWeather(view: View, weather: WeatherInfo) {
        view.findViewById<TextView>(R.id.WeatherStatus)?.text =
            "Today's weather: ${weather.current.weather.first().main}"
        view.findViewById<TextView>(R.id.Temperature)?.text =
            "Temperature: ${weather.current.temp} °F"
        view.findViewById<TextView>(R.id.TemperatureFeelsLike)?.text =
            "Feels like: ${weather.current.feels_like} °F"
        view.findViewById<TextView>(R.id.Humidity)?.text = "Humidity: ${weather.current.humidity} %"
        val stamp = Timestamp(weather.current.dt.toLong())
        val date = Date(stamp.time)
        viewModel.insertWeather(requireContext(),date.toString(),weather.current.temp)
        print(weather.current.dt)
        when (weather.current.weather.first().main) {
            "Clear" -> view.findViewById<ImageView>(R.id.weather_image)
                .setImageResource(R.drawable.clear)
            "Rain" -> view.findViewById<ImageView>(R.id.weather_image)
                .setImageResource(R.drawable.rain)
            "Clouds" -> view.findViewById<ImageView>(R.id.weather_image)
                .setImageResource(R.drawable.clouds)
            "Snow" -> view.findViewById<ImageView>(R.id.weather_image)
                .setImageResource(R.drawable.snow)
            else -> view.findViewById<ImageView>(R.id.weather_image)
                .setImageResource(R.drawable.clouds)
        }
        viewModel.uploadFile()
    }

    @SuppressLint("SetTextI18n")
    fun setWeatherNoGust(view: View, weather: WeatherInfoNoGust) {
        view.findViewById<TextView>(R.id.WeatherStatus)?.text =
            "Today's weather: ${weather.current.weather.first().main}"
        view.findViewById<TextView>(R.id.Temperature)?.text =
            "Temperature: ${weather.current.temp} °F"
        view.findViewById<TextView>(R.id.TemperatureFeelsLike)?.text =
            "Feels like: ${weather.current.feels_like} °F"
        view.findViewById<TextView>(R.id.Humidity)?.text = "Humidity: ${weather.current.humidity} %"
        val stamp = Timestamp(weather.current.dt.toLong()*1000)
        val date = Date(stamp.time)
        viewModel.insertWeather(requireContext(),date.toString(),weather.current.temp)
        when (weather.current.weather.first().main) {
            "Clear" -> view.findViewById<ImageView>(R.id.weather_image)
                .setImageResource(R.drawable.clear)
            "Rain" -> view.findViewById<ImageView>(R.id.weather_image)
                .setImageResource(R.drawable.rain)
            "Clouds" -> view.findViewById<ImageView>(R.id.weather_image)
                .setImageResource(R.drawable.clouds)
            "Snow" -> view.findViewById<ImageView>(R.id.weather_image)
                .setImageResource(R.drawable.snow)
            else -> view.findViewById<ImageView>(R.id.weather_image)
                .setImageResource(R.drawable.clouds)
        }
        viewModel.uploadFile()
    }
}