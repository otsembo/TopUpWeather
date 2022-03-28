package com.topupmama.weather.presentation

import android.os.Build.VERSION.SDK_INT
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.topupmama.weather.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@BindingAdapter("weatherIcon")
fun ImageView.bindWeatherIcon(icon:String){
    load(icon){
        crossfade(true)
        placeholder(R.drawable.weather_placeholder)
    }
}

@BindingAdapter("favIcon")
fun ImageView.setFavIcon(isFav:Boolean){
    if(isFav)
        load(R.drawable.ic_fav)
    else
        load(R.drawable.ic_not_fav)
}

@BindingAdapter("temp")
fun TextView.setTemperature(temp: Double){
    val tmp = "$temp °C"
    text = tmp
}


@BindingAdapter("show")
fun LinearLayout.show(isShown:Boolean){
    visibility = when(isShown){
        true -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("weatherData")
fun TextView.setWeatherData(data:String){
    text = StringBuilder().append("Weather: ").append(data).toString().trim()
}

@BindingAdapter("windSpeed")
fun TextView.setWindSpeed(data:Double){
    text = StringBuilder().append("Wind Speed: ").append(data).append(" km/h").toString().trim()
}

@BindingAdapter("humidity")
fun TextView.setHumidity(data:Double){
    text = StringBuilder().append("Humidity: ").append(data).append("%").toString().trim()
}

@BindingAdapter("weatherTemp")
fun TextView.setTemp(data:Double){
    text = StringBuilder().append("Temperature: ").append(data).append(" °C").toString().trim()
}

@BindingAdapter("airPressure")
fun TextView.setAirPressure(data:Double){
    text = StringBuilder().append("Air Pressure: ").append(data).append(" N/m2").toString().trim()
}

@BindingAdapter(value = ["city", "region"])
fun TextView.setRegion(city:String, region:String){
    text = StringBuilder().append(city).append(",\n").append(region)
}