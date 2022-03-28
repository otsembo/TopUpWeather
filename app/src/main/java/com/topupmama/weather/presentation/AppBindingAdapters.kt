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
    val tmp = "$temp C"
    text = tmp
}


@BindingAdapter("show")
fun LinearLayout.show(isShown:Boolean){
    visibility = when(isShown){
        true -> View.VISIBLE
        else -> View.GONE
    }
}
