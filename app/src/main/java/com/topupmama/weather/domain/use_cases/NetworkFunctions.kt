package com.topupmama.weather.domain.use_cases

import android.util.Log
import com.topupmama.weather.common.AppConstants.CITY_IDS
import com.topupmama.weather.common.AppConstants.WEATHER_UNITS
import com.topupmama.weather.common.AppResource
import com.topupmama.weather.data.network.dto.WeatherResponse
import com.topupmama.weather.data.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

object NetworkFunctions {

    class RetrieveNetworkUseCase
        @Inject constructor(private val repository: WeatherRepository, private val saveWeatherToDB: DbFunctions.SaveWeatherToDB) {

        operator fun invoke() : Flow<AppResource<WeatherResponse>> = flow{

            try{

                emit(AppResource.AppLoading<WeatherResponse>())

                val data = repository.fetchData(WEATHER_UNITS, CITY_IDS)

                emit(AppResource.AppSuccess<WeatherResponse>(data = data))

                DbFunctions.DeleteWeatherFromDB(repository)()

                saveWeatherToDB(data.list)

            }catch (e:Exception){

                emit(AppResource.AppError<WeatherResponse>(e.localizedMessage?:"An unexpected error occurred. Please check your internet connection and restart the application"))

            }

        }
    }

}