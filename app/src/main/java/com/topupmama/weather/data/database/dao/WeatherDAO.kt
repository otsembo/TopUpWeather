package com.topupmama.weather.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.topupmama.weather.data.model.WeatherData

@Dao
interface WeatherDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(weatherData: WeatherData)

    @Query("SELECT * FROM weather_data ORDER BY isFavorite DESC")
    fun getWeatherData() : LiveData<List<WeatherData>>

    @Query("SELECT * FROM weather_data LIMIT 1")
    fun getTopWeather() : WeatherData?

    @Query("UPDATE weather_data SET isFavorite = :fav WHERE id = :id")
    suspend fun updateWeather(fav: Boolean, id:Int)

    @Query("DELETE from weather_data")
    suspend fun clearWeatherDB()

}