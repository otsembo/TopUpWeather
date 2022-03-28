package com.topupmama.weather

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.topupmama.weather.data.repository.WeatherRepository
import com.topupmama.weather.data.workers.RefreshWeather
import com.topupmama.weather.data.workers.TestWorker
import com.topupmama.weather.domain.use_cases.NetworkFunctions
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class WeatherApp : Application(), Configuration.Provider{

    @Inject lateinit var workerFactory:HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        // refresh weather
        NetworkFunctions.NotificationRefresher()(applicationContext)

    }

    override fun getWorkManagerConfiguration(): Configuration  = Configuration.Builder().setWorkerFactory(workerFactory).build()


}