package com.topupmama.weather.presentation.home

import android.util.Log
import androidx.lifecycle.*
import com.topupmama.weather.common.AppResource
import com.topupmama.weather.common.AppState
import com.topupmama.weather.data.model.Favorites
import com.topupmama.weather.data.model.WeatherData
import com.topupmama.weather.data.network.dto.CityWeather
import com.topupmama.weather.data.network.dto.getWeatherData
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

    private val TAG:String = HomeVM::class.toString()

    private val weatherState:AppState<List<WeatherData>> = AppState()
    private val favoritesState: AppState<List<Favorites>> = AppState()

    private val _weatherLiveDataState:MutableLiveData<AppState<List<WeatherData>>> = MutableLiveData(weatherState)

    val weatherLiveData : LiveData<List<WeatherData>>
        get() = DbFunctions.RetrieveWeatherFromDB(repository)()

    private val favoritesLiveData : LiveData<List<Favorites>>
        get() = DbFunctions.RetrieveFavoritesFromDB(repository)()


    init {

        loadFromWeb()


    }


    fun loadFromWeb(){

        NetworkFunctions.RetrieveNetworkUseCase(repository, saveWeatherToDB)().onEach {

            when(it){

                is AppResource.AppLoading -> {
                    _weatherLiveDataState.value?.isLoading = true
                    Log.d(TAG, "sambusa: ${_weatherLiveDataState.value?.isLoading}")
                }

                is AppResource.AppError -> {
                    _weatherLiveDataState.value?.message = it.message
                    Log.d(TAG, "sambusa: ${_weatherLiveDataState.value?.message}")
                }

                is AppResource.AppSuccess -> {
                    _weatherLiveDataState.value?.data = setUpFavorites(it.data?.list)
                    Log.d(TAG, "sambusa: ${_weatherLiveDataState.value?.data}")
                }

            }

        }.launchIn(viewModelScope)

    }


    private fun setUpFavorites(cityWeatherData: List<CityWeather>?) : List<WeatherData>{
        val data = ArrayList<WeatherData>()
        val favoritesList = favoritesLiveData.value

        cityWeatherData?.forEach {
            cityWeather ->
                val weather = cityWeather.getWeatherData()
                weather.isFavorite = searchFavorites(cityWeather.id, favoritesList)
                data.add(weather)
        }
        data.sortByDescending { it.isFavorite }
        return data
    }


    private fun searchFavorites(cityId:Int, favorites: List<Favorites>?) : Boolean{
        var isFav = false
        favorites?.forEach {
            if(it.cityId == cityId) isFav = true
        }
        return isFav
    }


}