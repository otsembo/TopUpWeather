package com.topupmama.weather.di

import com.topupmama.weather.common.AppConstants
import com.topupmama.weather.data.network.OpenWeatherApi
import com.topupmama.weather.data.repository.WeatherRepository
import com.topupmama.weather.domain.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): OpenWeatherApi{
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: OpenWeatherApi) : WeatherRepository{
        return WeatherRepositoryImpl(api)
    }

}