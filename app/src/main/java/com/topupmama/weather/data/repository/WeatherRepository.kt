package com.topupmama.weather.data.repository

import com.topupmama.weather.data.network.dto.WeatherResponse

interface WeatherRepository {

    suspend fun fetchData(units:String, id:String) : WeatherResponse

}