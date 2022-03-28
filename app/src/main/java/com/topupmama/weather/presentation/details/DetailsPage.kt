package com.topupmama.weather.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.topupmama.weather.R
import com.topupmama.weather.common.AppConstants.TRANSITION_NAME
import com.topupmama.weather.common.AppConstants.WEATHER_KEY
import com.topupmama.weather.databinding.WeatherDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsPage : Fragment() {

    private lateinit var binding:WeatherDetailsBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.weather_details, container, false)

        arguments?.let {
            binding.weatherData = it.getParcelable(WEATHER_KEY)!!
            binding.imgData.transitionName = it.getString(TRANSITION_NAME)
        }



        return binding.root
    }



}