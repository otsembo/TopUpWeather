package com.topupmama.weather.data.model

data class WeatherData(
    val city:String,
    val temp:Double,
    val weather:String,
    val icon:String,
    val windSpeed:Double,
    val country:String,
    val pressure:Double,
    val humidity:Double
)
