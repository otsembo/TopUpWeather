package com.topupmama.weather.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.topupmama.weather.data.model.Favorites
import com.topupmama.weather.data.model.WeatherData

@Dao
interface FavoritesDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(favorites: Favorites)

    @Query("DELETE FROM weather_favs WHERE city_id = :id")
    suspend fun removeFromFavorites(id:Long)

    @Query("DELETE FROM weather_favs")
    suspend fun clearDB()

    @Query("SELECT * FROM weather_favs")
    fun getFavs() : LiveData<List<Favorites>>

    @Query("SELECT * FROM weather_favs LIMIT 1")
    suspend fun getTopCity() : Favorites?

}