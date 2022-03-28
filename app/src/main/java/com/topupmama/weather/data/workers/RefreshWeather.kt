package com.topupmama.weather.data.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.topupmama.weather.data.repository.WeatherRepository
import com.topupmama.weather.di.AppModule
import com.topupmama.weather.domain.repository.WeatherRepositoryImpl
import com.topupmama.weather.domain.use_cases.DbFunctions
import com.topupmama.weather.domain.use_cases.NetworkFunctions
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltWorker
class RefreshWeather
    @AssistedInject
    constructor (@Assisted val mCtx:Context, @Assisted workerParameters: WorkerParameters, private val workerDependencies: WorkerDependencies) : Worker(mCtx, workerParameters) {

    /*private val ioScope:CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val repository: WeatherRepository = AppModule.provideWeatherRepository(AppModule.provideApi(),AppModule.provideAppDB(mCtx))*/

    override fun doWork(): Result {

        return try {
            DbFunctions.ShowFavoritesNotifications(
                workerDependencies.scope,
                workerDependencies.repo,
                mCtx = mCtx
            )
            Result.success()
        }catch (e:Exception){
            Log.d("TAG", "doWork: ${e.message}")
            Result.retry()
        }finally {
            NetworkFunctions.RetrieveNetworkUseCase(workerDependencies.repo,
                DbFunctions.SaveWeatherToDB(workerDependencies.repo)).invoke()
                .launchIn(workerDependencies.scope)
        }

    }



}