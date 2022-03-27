package com.example.topmama.data.service

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.topmama.data.models.FavouriteRoomWeather
import com.example.topmama.data.models.RoomWeather

@Dao
interface WeatherDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addWeather(roomWeather: RoomWeather)

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addFavouriteWeather(roomWeather: FavouriteRoomWeather)

   @Update
   suspend fun updateWeatherInfo(roomWeather: RoomWeather)

   @Query("SELECT * FROM city_weather_table")
   fun readAllData(): LiveData<List<RoomWeather>>

}