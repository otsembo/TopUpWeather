package com.topupmama.weather.presentation.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.topupmama.weather.R
import com.topupmama.weather.data.model.WeatherData

class AppNotifications constructor(private val mCtx:Context){

    fun createNotification(weatherData: WeatherData) {

        val builder = NotificationCompat.Builder(mCtx, CHANNEL_ID)
            .setSmallIcon(R.drawable.weather_placeholder)
            .setContentTitle(weatherData.city)
            .setContentText("Temperature: ${ weatherData.temp }")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Temperature: ${ weatherData.temp }\n Weather: ${weatherData.weather}"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = mCtx.getString(R.string.channel_name)
            val descriptionText = mCtx.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                mCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }


        with(NotificationManagerCompat.from(mCtx)){
            notify(weatherData.id.toInt(),builder.build())
        }




    }


    companion object{

        const val CHANNEL_ID = "notifications_channel"

    }

}