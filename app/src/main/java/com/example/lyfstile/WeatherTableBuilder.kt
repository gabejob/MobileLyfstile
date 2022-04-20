package com.example.lyfstile

class WeatherTableBuilder {
    private var date : String ?= null
    private var temp : String ?= null

    public fun setDate(date : String?) : WeatherTableBuilder
    {
        if (date != null) {
            this.date = date
        }
        return this
    }

    fun setTemperature(temp: String?) : WeatherTableBuilder
    {
        if(temp != null)
            this.temp = temp
        return this
    }


    public fun createWeatherTable(): WeatherEntity? {return temp?.let { date?.let { it1 ->
        WeatherEntity(
            it1, it)
    } }
    }
}