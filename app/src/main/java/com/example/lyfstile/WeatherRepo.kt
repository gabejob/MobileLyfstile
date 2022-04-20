package com.example.lyfstile

import android.content.Context
import androidx.lifecycle.LiveData

class WeatherRepo public constructor(private val db: WeatherDBHandler) {

    companion object {
        private var db: WeatherDBHandler? = null
        var data: LiveData<List<WeatherEntity>>? = null

        fun insert(context: Context, date: String, temp: String) {
            db = initializeDB(context)

            val insert = WeatherEntity(
                date,
                temp
            )
            db!!.dao().insert(insert)
        }

        fun update(context: Context, date: String, temp: String) {
            db = initializeDB(context)
            db!!.dao().updateWeather(
                date,
                temp
            )
        }

        fun allWeather(context: Context): LiveData<List<WeatherEntity>>? {
            db = initializeDB(context)
            data = db?.dao()?.getAll()
            return data
        }

        fun getWeather(context: Context, date: String): LiveData<WeatherEntity>? {
            db = initializeDB(context)
            return db?.dao()?.getWeather(date)
        }

        fun initializeDB(context: Context): WeatherDBHandler {
            return WeatherDBHandler.getDatabaseClient(context)
        }
    }
}