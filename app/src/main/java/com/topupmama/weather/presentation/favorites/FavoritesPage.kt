package com.topupmama.weather.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.topupmama.weather.R
import com.topupmama.weather.common.AppConstants
import com.topupmama.weather.databinding.FavoritesFragmentBinding
import com.topupmama.weather.presentation.home.HomeVM
import com.topupmama.weather.presentation.home.WeatherListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesPage : Fragment() {

    private lateinit var binding: FavoritesFragmentBinding

    private val homeVM:HomeVM by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.favorites_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.homeViewModel = homeVM

        val adapter = WeatherListAdapter(
            WeatherListAdapter.WeatherItemClickListener {

                val weatherData = Bundle()
                weatherData.putParcelable(AppConstants.WEATHER_KEY, it)
                weatherData.putString(AppConstants.TRANSITION_NAME, "transition${it.id}")
                //val directions = HomeDirections.actionFragmentHomeToDetailsPage(it)
                binding.root.findNavController().navigate(R.id.action_fragmentHome_to_detailsPage, args = weatherData)
            },
            WeatherListAdapter.FavListener{

                homeVM.viewModelScope.launch {
                    homeVM.toggleFavorite(it)
                }

            }
        )

        binding.dataAdapter = adapter

        homeVM.favWeatherLiveData.observe(viewLifecycleOwner){
            adapter.submitList(it)

            if(it.isEmpty()){
                view?.let { it1 -> Snackbar.make(it1, "You have no favorites at this time", Snackbar.LENGTH_LONG).show() }
            }

        }

        return binding.root
    }

}