package com.example.topmama.data.service

import com.example.topmama.domain.models.Weather
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("current.json")
    suspend fun getWeather(
        @Query("key") accessKey: String,
        @Query("q") cityName: String,
        @Query("aqi") aqi: String
    ): Weather

}