package com.example.topmama.domain.usecases

import com.example.topmama.data.service.WeatherService
import com.example.topmama.domain.models.Weather
import com.example.topmama.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val service: WeatherService) {

    operator fun invoke(apiKey: String, city: String, aqi: String): Flow<Resource<Weather>> = flow {
        try {
            emit(Resource.Loading())
            val weather = service.getWeather(
                apiKey,
                city,
                aqi
            )
            emit(Resource.Success(weather))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))

        } catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }

}