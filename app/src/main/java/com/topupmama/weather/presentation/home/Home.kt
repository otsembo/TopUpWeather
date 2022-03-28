package com.topupmama.weather.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.topupmama.weather.R
import com.topupmama.weather.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class Home : Fragment() {

    private val homeVM:HomeVM by viewModels()
    private lateinit var binding:HomeFragmentBinding

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        val adapter = WeatherListAdapter(
            WeatherListAdapter.WeatherItemClickListener {

            },
            WeatherListAdapter.FavListener{

                ioScope.launch {
                    homeVM.toggleFavorite(it)
                }
            }
        )

        binding.homeViewModel = homeVM
        binding.lifecycleOwner = viewLifecycleOwner
        binding.dataAdapter = adapter


        homeVM.weatherLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        homeVM.favLiveData.observe(viewLifecycleOwner){
            homeVM.userFavorites = it
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}