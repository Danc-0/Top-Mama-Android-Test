package com.example.topmama.domain.usecases

import com.example.topmama.data.apiservice.WeatherService
import com.example.topmama.domain.models.Weather
import com.example.topmama.utils.Resource
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val service: WeatherService) {

    operator fun invoke(): Flow<Resource<Weather>> = flow {
        try {
            emit(Resource.Loading())
            val weather = service.getWeather()
            emit(Resource.Success(weather))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))

        } catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }

}