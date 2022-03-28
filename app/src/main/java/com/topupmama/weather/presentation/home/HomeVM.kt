package com.topupmama.weather.presentation.home

import android.util.Log
import android.widget.Toast
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
import kotlinx.coroutines.launch
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

    val favWeatherLiveData : LiveData<List<WeatherData>>
        get() = DbFunctions.RetrieveFavWeatherFromDB(repository)()

    val appState : LiveData<AppState<List<WeatherData>>>
        get() = _weatherLiveDataState

    val favLiveData : LiveData<List<Favorites>>
        get() = repository.loadFavorites()

    var userFavorites : List<Favorites> = emptyList()

    init {

        loadFromWeb()

    }


    private fun loadFromWeb(){

        NetworkFunctions.RetrieveNetworkUseCase(repository, saveWeatherToDB)().onEach {

            when(it){

                is AppResource.AppLoading -> {

                    _weatherLiveDataState.value = AppState(isLoading = true)
                }

                is AppResource.AppError -> {
                    _weatherLiveDataState.value = AppState(message = it.message)
                }

                is AppResource.AppSuccess -> {
                    _weatherLiveDataState.value = AppState(data =  setUpFavorites(it.data?.list))
                }

            }

        }.launchIn(viewModelScope)

    }


    private fun setUpFavorites(cityWeatherData: List<CityWeather>?) : List<WeatherData>{
        val data = ArrayList<WeatherData>()
        val favoritesList = userFavorites

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


    suspend fun toggleFavorite(cityId: Long){
        val isFav = searchFavorites(cityId = cityId.toInt(), favorites = userFavorites)
        Log.d(TAG, "toggleFavorite: $cityId => $isFav => $userFavorites")
        DbFunctions.ToggleFavorites(repository).invoke(cityId, isFav)
    }



}