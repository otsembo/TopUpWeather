package com.topupmama.weather.presentation.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.topupmama.weather.R
import com.topupmama.weather.common.AppConstants.TRANSITION_NAME
import com.topupmama.weather.common.AppConstants.WEATHER_KEY
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

                val weatherData = Bundle()
                weatherData.putParcelable(WEATHER_KEY, it)
                weatherData.putString(TRANSITION_NAME, "transition${it.id}")
                //val directions = HomeDirections.actionFragmentHomeToDetailsPage(it)
                binding.root.findNavController().navigate(R.id.action_fragmentHome_to_detailsPage, args = weatherData)

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

        binding.edtSearchCity.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}