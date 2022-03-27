package com.example.topmama.domain.usecases

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.topmama.data.models.RoomWeather
import com.example.topmama.data.service.WeatherDatabase
import com.example.topmama.domain.repositories.RoomWeatherRepository
import com.example.topmama.domain.repositories.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class RoomWeatherUseCase(application: Application) {

    private val readAllData: LiveData<List<RoomWeather>>
    private val repository: RoomWeatherRepository

    init {
        val weatherDao = WeatherDatabase.getDatabase(application.applicationContext).weatherDao()
        repository = RoomWeatherRepository(weatherDao)
        readAllData = repository.readAllData
    }

    operator fun invoke(): Flow<LiveData<List<RoomWeather>>> = flow {
        try {
            emit(readAllData)
        } catch(e: Exception){
            e.localizedMessage
        }
    }

}