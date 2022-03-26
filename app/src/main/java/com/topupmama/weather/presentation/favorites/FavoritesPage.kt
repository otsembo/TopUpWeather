package com.topupmama.weather.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.topupmama.weather.R
import com.topupmama.weather.databinding.FavoritesFragmentBinding
import com.topupmama.weather.presentation.home.HomeVM
import com.topupmama.weather.presentation.home.WeatherListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesPage : Fragment() {

    private lateinit var binding: FavoritesFragmentBinding

    private val homeVM:HomeVM by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.favorites_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.homeViewModel = homeVM

        val adapter = WeatherListAdapter(WeatherListAdapter.WeatherItemClickListener {

        })

        binding.dataAdapter = adapter

        homeVM.favWeatherLiveData.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        return binding.root
    }

}