package com.topupmama.weather.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "weather_favs")
data class Favorites(
    @PrimaryKey(autoGenerate = true)
    val id:Long,

    @ColumnInfo(name = "city_id")
    val cityId:Int

)
