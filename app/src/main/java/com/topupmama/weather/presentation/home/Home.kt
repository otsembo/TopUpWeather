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

@AndroidEntryPoint
class Home : Fragment() {

    private val homeVM:HomeVM by viewModels()
    private lateinit var binding:HomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.homeViewModel = homeVM
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = WeatherListAdapter(
            WeatherListAdapter.WeatherItemClickListener {

            }
        )
        binding.dataAdapter = adapter
        homeVM.weatherLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}