package com.topupmama.weather.domain.use_cases

import com.topupmama.weather.data.network.dto.CityWeather
import com.topupmama.weather.data.network.dto.getWeatherData
import com.topupmama.weather.data.repository.WeatherRepository
import javax.inject.Inject

object DbFunctions {


    class SaveWeatherToDB
        @Inject constructor(private val repository: WeatherRepository) {
           suspend operator fun invoke(cityWeather: List<CityWeather>){
                cityWeather.forEach {
                    repository.saveWeatherToAppDB(it.getWeatherData())
                }
            }
        }

    class RetrieveWeatherFromDB
        @Inject constructor(private val repository: WeatherRepository){

        }

}