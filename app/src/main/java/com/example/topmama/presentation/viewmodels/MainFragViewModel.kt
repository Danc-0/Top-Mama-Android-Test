package com.example.topmama.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topmama.domain.models.Weather
import com.example.topmama.domain.usecases.WeatherUseCase
import com.example.topmama.utils.Resource
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainFragViewModel @Inject constructor( private val useCase: WeatherUseCase): ViewModel() {

    private val TAG = "MainFragViewModel"
    val weatherData = MutableLiveData<Weather>()

    fun getWeatherData() {
        useCase().onEach { weather ->
            run {
                when (weather) {
                    is Resource.Loading -> {
                        Log.d(TAG, "getWeatherData: Loading...")

                    }
                    is Resource.Success -> {
                        Log.d(TAG, "getWeatherData: Successful")
                        weatherData.postValue(weather.data!!)
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "getWeatherData: Error occurred")
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}