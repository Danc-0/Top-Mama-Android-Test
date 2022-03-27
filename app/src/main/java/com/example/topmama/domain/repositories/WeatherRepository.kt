package com.example.topmama.domain.repositories

import com.example.topmama.domain.models.Weather

interface WeatherRepository {

    suspend fun getWeather() : Weather

}