package com.topupmama.weather.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherData(
    @PrimaryKey(autoGenerate = true)
    val id:Long,

    val city:String,
    val temp:Double,
    val weather:String,
    val icon:String,
    val windSpeed:Double,
    val country:String,
    val pressure:Double,
    val humidity:Double,
    var isFavorite:Boolean
)
