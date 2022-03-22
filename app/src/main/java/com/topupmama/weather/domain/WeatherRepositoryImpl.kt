package com.topupmama.weather.domain

import com.topupmama.weather.data.network.OpenWeatherApi
import com.topupmama.weather.data.network.dto.WeatherResponse
import com.topupmama.weather.data.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl

    @Inject
    constructor(private val api:OpenWeatherApi) : WeatherRepository{

    override suspend fun fetchData(units: String, id: String): WeatherResponse {
        return api.fetchWeather(units, id)
    }
}