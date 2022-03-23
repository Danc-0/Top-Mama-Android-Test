package com.example.topmama.domain.repositories

import com.example.topmama.domain.models.Weather
import io.reactivex.Single

interface WeatherRepository {

    suspend fun getWeather() : Weather

}