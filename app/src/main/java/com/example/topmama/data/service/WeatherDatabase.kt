package com.example.topmama.data.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.topmama.data.models.FavouriteRoomWeather
import com.example.topmama.data.models.RoomWeather
import java.text.Collator.PRIMARY

@Database(entities = [RoomWeather::class, FavouriteRoomWeather::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {

        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "city_weather"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }



}