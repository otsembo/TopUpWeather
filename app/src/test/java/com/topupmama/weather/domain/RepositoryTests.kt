package com.topupmama.weather.domain

import com.topupmama.weather.data.network.OpenWeatherApi
import com.topupmama.weather.data.network.dto.WeatherResponse
import com.topupmama.weather.data.repository.WeatherRepository
import com.topupmama.weather.domain.repository.WeatherRepositoryImpl
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RepositoryTests {

    private lateinit var repository: WeatherRepositoryImpl
    private lateinit var weatherApi: OpenWeatherApi
    private lateinit var weatherResponse: WeatherResponse

    @Before
    fun setUp() = runBlocking {

        weatherResponse = WeatherResponse(0, listOf())

        weatherApi = mock<OpenWeatherApi> ().apply{
            whenever(fetchWeather("","")).thenReturn(weatherResponse);
        }

        repository = WeatherRepositoryImpl(weatherApi)

    }

    @Test
    fun `the repository fetchWeather() function should return WeatherResponse`() = runBlocking{
        assertEquals("Method should return weather response class",repository.fetchData("",""), weatherResponse)
    }

}