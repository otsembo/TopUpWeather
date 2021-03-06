package com.topupmama.weather.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "weather_data")
data class WeatherData(
    @PrimaryKey
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
) : Parcelable
