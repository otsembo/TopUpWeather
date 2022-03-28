package com.topupmama.weather.data.repository

import androidx.lifecycle.LiveData
import com.topupmama.weather.data.model.Favorites
import com.topupmama.weather.data.model.WeatherData
import com.topupmama.weather.data.network.dto.WeatherResponse

interface WeatherRepository {

    suspend fun fetchData(units:String, id:String) : WeatherResponse

    suspend fun saveWeatherToAppDB(weatherData: WeatherData)

    suspend fun deleteWeatherFromDB()

    suspend fun saveFavorites(favorites: Favorites)

    fun loadWeatherData() : LiveData<List<WeatherData>>

    fun loadFavoriteWeather() : LiveData<List<WeatherData>>

    fun loadFavorites() : LiveData<List<Favorites>>

    suspend fun getFavorites() : List<Favorites>

    suspend fun deleteFromFavorites(cityId:Long)

    suspend fun updateWeather(isFav:Int, cityId: Long)


}