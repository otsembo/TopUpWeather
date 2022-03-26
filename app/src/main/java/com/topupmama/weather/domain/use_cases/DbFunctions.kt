package com.topupmama.weather.domain.use_cases

import androidx.lifecycle.LiveData
import com.topupmama.weather.data.model.Favorites
import com.topupmama.weather.data.model.WeatherData
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
            operator fun invoke() : LiveData<List<WeatherData>>{
                return repository.loadWeatherData();
            }
        }


    class RetrieveFavoritesFromDB
        @Inject constructor(private val repository: WeatherRepository){
            operator fun invoke() : LiveData<List<Favorites>>{
                return repository.loadFavorites()
            }
        }

}