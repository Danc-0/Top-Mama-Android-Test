package com.example.topmama.data.repository

import com.example.topmama.data.apiservice.WeatherService
import com.example.topmama.domain.models.Weather
import com.example.topmama.domain.repositories.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor( val service: WeatherService) : WeatherRepository {

    override fun getWeather(): Single<Weather> {
        TODO("Not yet implemented")
    }
}