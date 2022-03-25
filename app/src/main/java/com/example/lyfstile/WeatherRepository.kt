package com.example.lyfstile

import android.app.Application
import androidx.lifecycle.MutableLiveData

class WeatherRepository private constructor(application: Application) {
    private val userData: MutableLiveData<User> = MutableLiveData<User>()

    val data: MutableLiveData<User>
        get() = userData

    private fun loadData() {
       // FetchWeatherTask().execute(mLocation)
    }


    fun get(): MutableLiveData<User>? {
        return null
    }

    companion object {
        private var instance: WeatherRepository? = null
        @Synchronized
        fun getInstance(application: Application): WeatherRepository {
            if (instance == null) {
                instance = WeatherRepository(application)
            }
            return instance as WeatherRepository
        }
    }

    init {


    }
}