package com.example.lyfstile

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.temporal.Temporal

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntity: WeatherEntity?)

    @Query(
        "UPDATE weather_table SET date=:date, temperature=:temp WHERE date=:date"
    )
    fun updateWeather(
        date: String?,
        temp: String?
        )

    @Query("DELETE FROM weather_table WHERE date = :date")
    fun deleteAll(date: String)

    @Query("SELECT * from weather_table ORDER BY date ASC")
    fun getAll(): LiveData<List<WeatherEntity>>

    @Query("SELECT * from weather_table WHERE date = :date")
    fun getWeather(date: String): LiveData<WeatherEntity>
}