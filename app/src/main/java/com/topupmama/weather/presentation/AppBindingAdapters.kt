package com.topupmama.weather.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.topupmama.weather.R

@BindingAdapter("weatherIcon")
fun bindWeatherIcon(icon:String, img:ImageView){
    img.load(icon){
        crossfade(true)
        placeholder(R.drawable.weather_placeholder)
    }
}

@BindingAdapter("favIcon")
fun setFavIcon(isFav:Boolean, img: ImageView){
    if(isFav)
        img.load(R.drawable.ic_fav)
    else
        img.load(R.drawable.ic_not_fav)
}

@BindingAdapter("temp")
fun setTemperature(temp: Double, txt:TextView){
    val tmp = "$temp C"
    txt.text = tmp
}
