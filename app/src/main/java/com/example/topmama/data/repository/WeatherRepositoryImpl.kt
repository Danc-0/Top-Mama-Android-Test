package com.example.topmama.data.repository

import com.example.topmama.data.service.WeatherService
import com.example.topmama.domain.models.Weather
import com.example.topmama.domain.repositories.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val service: WeatherService) : WeatherRepository {

    override suspend fun getWeather(): Weather {
        TODO()
//        return service.getWeather()
    }
}