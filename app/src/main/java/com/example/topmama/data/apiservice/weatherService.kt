package com.example.topmama.data.apiservice

import com.example.topmama.domain.models.Weather
import io.reactivex.Single
import retrofit2.http.GET

interface WeatherService {

    @GET("current?access_key=d12abaefc4545ec48b1c3e1e01713861&query=kakamega")
    suspend fun getWeather(): Single<Weather>

}