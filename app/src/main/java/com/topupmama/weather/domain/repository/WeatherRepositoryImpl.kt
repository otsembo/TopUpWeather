package com.topupmama.weather.domain.repository

import androidx.lifecycle.LiveData
import com.topupmama.weather.common.Api
import com.topupmama.weather.data.database.AppDatabase
import com.topupmama.weather.data.model.Favorites
import com.topupmama.weather.data.model.WeatherData
import com.topupmama.weather.data.network.OpenWeatherApi
import com.topupmama.weather.data.network.dto.WeatherResponse
import com.topupmama.weather.data.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl

    @Inject
    constructor(private val api:OpenWeatherApi, private val db:AppDatabase) : WeatherRepository{

    override suspend fun fetchData(units: String, id: String): WeatherResponse {
        return api.fetchWeather(units, id, Api.KEY)
    }

    override suspend fun saveWeatherToAppDB(weatherData: WeatherData) {
        db.weatherDAO.insert(weatherData)
    }

    override suspend fun saveFavorites(favorites: Favorites) {
        db.favoritesDAO.insert(favorites)
    }

    override fun loadWeatherData(): LiveData<List<WeatherData>> {
        return db.weatherDAO.getWeatherData()
    }

    override fun loadFavorites(): LiveData<List<Favorites>> {
        return db.favoritesDAO.getFavs()
    }

    override suspend fun deleteFromFavorites(cityId: Long) {
        return db.favoritesDAO.removeFromFavorites(cityId)
    }

    override suspend fun deleteWeatherFromDB() {
        return db.weatherDAO.clearWeatherDB()
    }
}