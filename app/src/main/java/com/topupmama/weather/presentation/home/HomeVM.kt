package com.topupmama.weather.presentation.home

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topupmama.weather.common.AppResource
import com.topupmama.weather.common.AppState
import com.topupmama.weather.data.model.Favorites
import com.topupmama.weather.data.model.WeatherData
import com.topupmama.weather.data.repository.WeatherRepository
import com.topupmama.weather.domain.use_cases.DbFunctions
import com.topupmama.weather.domain.use_cases.NetworkFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeVM
    @Inject constructor(
        private val repository: WeatherRepository, private val saveWeatherToDB: DbFunctions.SaveWeatherToDB
    ) : ViewModel(){

    private val weatherState:AppState<List<WeatherData>> = AppState()
    private val favoritesState: AppState<List<Favorites>> = AppState()

    private val weatherLiveData:MutableLiveData<AppState<List<WeatherData>>> = MutableLiveData(weatherState)




    fun loadFromWeb(){

        NetworkFunctions.RetrieveNetworkUseCase(repository, saveWeatherToDB)().onEach {

            when(it){

                is AppResource.AppLoading -> {
                    weatherState.isLoading = true
                }

            }

        }.launchIn(viewModelScope)

    }


}