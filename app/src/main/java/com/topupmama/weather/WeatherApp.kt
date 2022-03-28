package com.topupmama.weather

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.topupmama.weather.domain.use_cases.NetworkFunctions
import dagger.hilt.android.HiltAndroidApp
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