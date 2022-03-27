package com.example.topmama.domain.repositories

import androidx.lifecycle.LiveData
import com.example.topmama.data.models.FavouriteRoomWeather
import com.example.topmama.data.models.RoomWeather
import com.example.topmama.data.service.WeatherDao
import javax.inject.Inject

class RoomWeatherRepository @Inject constructor (private val weatherDao: WeatherDao) {

    val readAllData: LiveData<List<RoomWeather>> = weatherDao.readAllData()

    suspend fun addWeather (roomWeather: RoomWeather) {
        weatherDao.addWeather(roomWeather)
    }

    suspend fun addingFavourite(roomWeather: FavouriteRoomWeather){
        weatherDao.addFavouriteWeather(roomWeather)
    }

    suspend fun updateWithFavourite(roomWeather: RoomWeather){
        weatherDao.updateWeatherInfo(roomWeather)
    }

}