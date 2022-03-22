package com.topupmama.weather

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.topupmama.weather.data.database.AppDatabase
import com.topupmama.weather.data.database.dao.FavoritesDAO
import com.topupmama.weather.data.database.dao.WeatherDAO
import com.topupmama.weather.data.model.Favorites
import com.topupmama.weather.data.model.WeatherData
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var weatherDAO: WeatherDAO
    private lateinit var favoritesDAO: FavoritesDAO
    private lateinit var db:AppDatabase
    private lateinit var mCtx:Context
    private lateinit var fakeWeatherData: WeatherData


    @Before
    fun setUpDB(){
        mCtx = InstrumentationRegistry.getInstrumentation().context

        db = Room.inMemoryDatabaseBuilder(mCtx, AppDatabase::class.java).allowMainThreadQueries().build()

        weatherDAO = db.weatherDAO
        favoritesDAO = db.favoritesDAO

        fakeWeatherData = WeatherData(0,"",0.0,"", "", 0.0, "", 0.0, 0.0, false)

    }


    @After
    @Throws(IOException::class)
    fun closeDBOps(){
        db.close()
    }

    private suspend fun addFavs(){
        val favorite = Favorites(0,10)
        favoritesDAO.insert(favorite)
    }

    private suspend fun addWeather(){
        weatherDAO.insert(fakeWeatherData)
    }

    @Test
    @Throws(Exception::class)
    fun insertDataToFavorites () = runBlocking {
        addFavs()
        assertEquals(favoritesDAO.getTopCity()?.cityId, 10)

    }

    @Test
    @Throws(Exception::class)
    fun deleteFromFavorites() = runBlocking {
        addFavs()
        favoritesDAO.clearDB()
        assertNull(favoritesDAO.getTopCity())
    }


    @Test
    @Throws(Exception::class)
    fun insertIntoWeatherData() = runBlocking {
        addWeather()
        assertEquals(weatherDAO.getTopWeather()?.temp, 0.0)
    }

    @Test
    @Throws(Exception::class)
    fun updateWeatherData() = runBlocking {
        addWeather()
        weatherDAO.updateWeather(true, 1)
        assertEquals(weatherDAO.getTopWeather()?.isFavorite, true)
    }









}