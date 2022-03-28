package com.topupmama.weather.domain.use_cases

import android.content.Context
import android.util.Log
import androidx.work.*
import com.topupmama.weather.common.AppConstants.CITY_IDS
import com.topupmama.weather.common.AppConstants.WEATHER_UNITS
import com.topupmama.weather.common.AppResource
import com.topupmama.weather.data.network.dto.WeatherResponse
import com.topupmama.weather.data.repository.WeatherRepository
import com.topupmama.weather.data.workers.RefreshWeather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

object NetworkFunctions {

    class RetrieveNetworkUseCase
        @Inject constructor(private val repository: WeatherRepository, private val saveWeatherToDB: DbFunctions.SaveWeatherToDB) {

        operator fun invoke() : Flow<AppResource<WeatherResponse>> = flow{

            try{

                emit(AppResource.AppLoading<WeatherResponse>())

                val data = repository.fetchData(WEATHER_UNITS, CITY_IDS)

                emit(AppResource.AppSuccess<WeatherResponse>(data = data))

                saveWeatherToDB(data.list)

            }catch (e:Exception){

                emit(AppResource.AppError<WeatherResponse>(e.localizedMessage?:"An unexpected error occurred. Please check your internet connection and restart the application"))

            }

        }
    }


    class NotificationRefresher(){

        operator fun invoke(mCtx:Context)  {
            val constraints = Constraints.Builder()
                .build()

            val updateRequest = PeriodicWorkRequestBuilder<RefreshWeather>(1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(mCtx).enqueueUniquePeriodicWork("RefreshWeather",ExistingPeriodicWorkPolicy.REPLACE, updateRequest)
        }

    }

}