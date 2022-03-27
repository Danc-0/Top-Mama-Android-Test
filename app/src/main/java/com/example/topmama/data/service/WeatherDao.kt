package com.example.topmama.data.service

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.topmama.data.models.FavouriteRoomWeather
import com.example.topmama.data.models.RoomWeather

@Dao
interface WeatherDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addWeather(roomWeather: RoomWeather)

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addFavouriteWeather(roomWeather: FavouriteRoomWeather)

   @Query("SELECT * FROM city_weather_table")
   fun readAllData(): LiveData<List<RoomWeather>>

}