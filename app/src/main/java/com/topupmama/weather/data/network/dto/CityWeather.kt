package com.topupmama.weather.data.network.dto

import com.topupmama.weather.data.model.WeatherData

data class CityWeather(
    val clouds: Clouds,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

fun CityWeather.getWeatherData() : WeatherData{
    return WeatherData(
        id = id.toLong(),
        city = name, temp = main.temp, weather = weather[0].main,
        icon = weather[0].icon, windSpeed = wind.speed,
        country = sys.country, pressure = main.pressure.toDouble(),
        humidity = main.humidity.toDouble(), false
    )
}