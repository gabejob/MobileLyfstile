package com.example.lyfstile

import androidx.annotation.NonNull
import androidx.room.*


@Entity(tableName = "weather_table")
data class WeatherEntity constructor(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "date")
    var date: String,

    @NonNull
    @ColumnInfo(name = "temperature")
    var temperature: String,
    ) {
}
