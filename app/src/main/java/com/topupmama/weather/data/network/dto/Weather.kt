package com.topupmama.weather.data.network.dto

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)