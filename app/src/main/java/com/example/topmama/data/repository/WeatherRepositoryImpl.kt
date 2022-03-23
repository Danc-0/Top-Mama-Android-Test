package com.example.topmama.data.repository

import com.example.topmama.data.apiservice.WeatherService
import com.example.topmama.domain.models.Weather
import com.example.topmama.domain.repositories.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val service: WeatherService) : WeatherRepository {

    override suspend fun getWeather(): Weather {
        return service.getWeather()
    }
}