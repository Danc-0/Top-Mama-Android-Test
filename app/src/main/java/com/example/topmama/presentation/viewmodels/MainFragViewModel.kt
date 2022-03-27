package com.example.topmama.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topmama.data.models.FavouriteRoomWeather
import com.example.topmama.data.models.RoomWeather
import com.example.topmama.data.service.WeatherDatabase
import com.example.topmama.domain.models.Weather
import com.example.topmama.domain.repositories.RoomWeatherRepository
import com.example.topmama.domain.usecases.WeatherUseCase
import com.example.topmama.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragViewModel @Inject constructor(
    private val useCase: WeatherUseCase,
    application: Application
) : ViewModel() {

    private val TAG = "MainFragViewModel"
    val weatherData = MutableLiveData<Weather>()
    val readAllData: LiveData<List<RoomWeather>>
    private val repository: RoomWeatherRepository


    fun getWeatherData(apiKey: String, city: String, aqi: String) {
        useCase(apiKey, city, aqi).onEach { weather ->
            run {
                when (weather) {
                    is Resource.Loading -> {
                        Log.d(TAG, "getWeatherData: Loading...")

                    }
                    is Resource.Success -> {
                        weatherData.postValue(weather.data!!)
                        val roomWeather = RoomWeather(
                            0,
                            weather.data.location.country,
                            weather.data.location.lat,
                            weather.data.location.localtime,
                            weather.data.location.localtime_epoch,
                            weather.data.location.lon,
                            weather.data.location.name,
                            weather.data.location.region,
                            weather.data.location.tz_id,
                            weather.data.current.cloud,
                            weather.data.current.feelslike_c,
                            weather.data.current.feelslike_f,
                            weather.data.current.gust_kph,
                            weather.data.current.gust_mph,
                            weather.data.current.humidity,
                            weather.data.current.is_day,
                            weather.data.current.last_updated,
                            weather.data.current.last_updated_epoch,
                            weather.data.current.precip_in,
                            weather.data.current.precip_mm,
                            weather.data.current.pressure_in,
                            weather.data.current.pressure_mb,
                            weather.data.current.temp_c,
                            weather.data.current.temp_f,
                            weather.data.current.uv,
                            weather.data.current.vis_km,
                            weather.data.current.vis_miles,
                            weather.data.current.wind_degree,
                            weather.data.current.wind_dir,
                            weather.data.current.wind_kph,
                            weather.data.current.wind_mph,
                            weather.data.current.condition.code,
                            weather.data.current.condition.icon,
                            weather.data.current.condition.text
                        )

                        addingWeather(roomWeather)

                    }

                    is Resource.Error -> {
                        Log.d(TAG, "getWeatherData: Error occurred")
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    init {
        val weatherDao = WeatherDatabase.getDatabase(application.applicationContext).weatherDao()
        repository = RoomWeatherRepository(weatherDao)
        readAllData = repository.readAllData
    }

    fun addingWeather(weather: RoomWeather) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWeather(weather)

        }
    }

    fun addingWeatherFavourite(favourite: FavouriteRoomWeather) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addingFavourite(favourite)
        }
    }

    fun updateWithFavourite(weather: RoomWeather){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateWithFavourite(weather)
        }
    }

}