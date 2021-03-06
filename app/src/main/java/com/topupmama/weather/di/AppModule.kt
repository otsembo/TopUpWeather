package com.topupmama.weather.di

import android.content.Context
import com.topupmama.weather.common.AppConstants
import com.topupmama.weather.data.database.AppDatabase
import com.topupmama.weather.data.network.OpenWeatherApi
import com.topupmama.weather.data.repository.WeatherRepository
import com.topupmama.weather.data.workers.WorkerDependencies
import com.topupmama.weather.domain.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    fun provideWeatherRepository(api: OpenWeatherApi, db:AppDatabase) : WeatherRepository{
        return WeatherRepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideAppDB(@ApplicationContext context:Context) : AppDatabase{
        return AppDatabase.getDBInstance(context)
    }

    @Provides
    @Singleton
    fun provideIOScope() : CoroutineScope{
        return CoroutineScope(Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun providerWorkerDeps(ioScope: CoroutineScope, repository: WeatherRepository): WorkerDependencies{
        return WorkerDependencies(ioScope, repository)
    }

}