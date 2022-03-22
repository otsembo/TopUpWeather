package com.topupmama.weather.data.network

import com.topupmama.weather.data.network.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("group")
    suspend fun fetchWeather(
        @Query("units")  units:String,
        @Query("id")  id:String,
    ) : WeatherResponse

}