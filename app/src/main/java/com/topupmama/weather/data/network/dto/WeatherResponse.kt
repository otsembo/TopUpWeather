package com.topupmama.weather.data.network.dto

data class WeatherResponse(
    val cnt: Int,
    val list: List<CityWeather>
)
