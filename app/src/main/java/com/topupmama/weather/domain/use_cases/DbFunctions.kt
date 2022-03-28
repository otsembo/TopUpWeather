package com.topupmama.weather.domain.use_cases

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.topupmama.weather.data.model.Favorites
import com.topupmama.weather.data.model.WeatherData
import com.topupmama.weather.data.network.dto.CityWeather
import com.topupmama.weather.data.network.dto.getWeatherData
import com.topupmama.weather.data.repository.WeatherRepository
import com.topupmama.weather.presentation.notifications.AppNotifications
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

object DbFunctions {


    class SaveWeatherToDB
        @Inject constructor(private val repository: WeatherRepository) {
           suspend operator fun invoke(cityWeather: List<CityWeather>){
               val data = setUpFavorites(cityWeather)
               data.forEach {
                   repository.saveWeatherToAppDB(it)
               }
            }

            private suspend fun setUpFavorites(cityWeatherData: List<CityWeather>?) : List<WeatherData>{
                val data = ArrayList<WeatherData>()
                val favoritesList = repository.getFavorites()

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
            suspend operator fun invoke() : List<Favorites>{
                return repository.getFavorites()
            }
        }

    class ToggleFavorites
        @Inject constructor(private val repository: WeatherRepository){
            suspend operator fun invoke(id:Long, isFav:Boolean){
                if(isFav) {
                    repository.deleteFromFavorites(id)
                    repository.updateWeather(0, id)
                }
                else {
                    repository.saveFavorites(Favorites(0,id.toInt()))
                    repository.updateWeather(1, id)
                }


            }
        }

    class ShowFavoritesNotifications(private val ioCoroutineScope: CoroutineScope, private val repository: WeatherRepository, private val mCtx:Context){
        operator fun invoke(){
            ioCoroutineScope.launch {
                repository.readFavoritesList().forEach {
                    AppNotifications(mCtx).createNotification(it)
                }
            }
        }
    }

}