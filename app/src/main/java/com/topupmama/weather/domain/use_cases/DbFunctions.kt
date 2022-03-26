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
           operator fun invoke(cityWeather: List<CityWeather>){
               setUpFavorites(cityWeather)
            }

            private fun setUpFavorites(cityWeatherData: List<CityWeather>?) : List<WeatherData>{
                val data = ArrayList<WeatherData>()
                val favoritesList = repository.loadFavorites().value

                cityWeatherData?.forEach {
                        cityWeather ->
                    val weather = cityWeather.getWeatherData()
                    weather.isFavorite = searchFavorites(cityWeather.id, favoritesList)
                    data.add(weather)
                }
                data.sortByDescending { it.isFavorite }
                return data
            }

            private fun searchFavorites(cityId:Int, favorites: List<Favorites>?) : Boolean{
                var isFav = false
                favorites?.forEach {
                    if(it.cityId == cityId) isFav = true
                }
                return isFav
            }
        }

    class DeleteWeatherFromDB
    @Inject constructor(private val repository: WeatherRepository) {
        suspend operator fun invoke(){
            repository.deleteWeatherFromDB()
        }
    }

    class RetrieveWeatherFromDB
        @Inject constructor(private val repository: WeatherRepository){
            operator fun invoke() : LiveData<List<WeatherData>>{
                return repository.loadWeatherData();
            }
        }

    class RetrieveFavWeatherFromDB
    @Inject constructor(private val repository: WeatherRepository){
        operator fun invoke() : LiveData<List<WeatherData>>{
            return repository.loadFavoriteWeather();
        }
    }

    class RetrieveFavoritesFromDB
        @Inject constructor(private val repository: WeatherRepository){
            operator fun invoke() : LiveData<List<Favorites>>{
                return repository.loadFavorites()
            }
        }

}