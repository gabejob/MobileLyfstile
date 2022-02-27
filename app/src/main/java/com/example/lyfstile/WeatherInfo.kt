package com.example.lyfstile

data class WeatherInfo(
    var lat: String,
    var lon: String,
    var timezone: String,
    var timezone_offset: String,
    var current: Current
)

data class Current(
    var dt: String,
    var sunrise: String,
    var sunset: String,
    var temp: String,
    var feels_like: String,
    var pressure: String,
    var humidity: String,
    var dew_point: String,
    var uvi: String,
    var clouds: String,
    var visibility: String,
    var wind_speed: String,
    var wind_deg: String,
    var wind_gust: String,
    var weather: MutableList<Weather>
)

data class Weather(
    var id: String,
    var main: String,
    var description: String,
    var icon: String
)