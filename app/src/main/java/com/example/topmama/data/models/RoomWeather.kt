package com.example.topmama.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.topmama.domain.models.AirQuality
import com.example.topmama.domain.models.Condition
import com.example.topmama.domain.models.Current
import com.example.topmama.domain.models.Location
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "city_weather_table", indices = [Index(value = ["key"], unique = true)])
data class RoomWeather(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo( name= "key")
    val Key: Int,
    val country: String? = null,
    val lat: Double? = null,
    val localtime: String? = null,
    val localtime_epoch: Int? = null,
    val lon: Double? = null,
    val name: String? = null,
    val region: String? = null,
    val tz_id: String? = null,
    val cloud: Int? = null,
    val feelslike_c: Double? = null,
    val feelslike_f: Double? = null,
    val gust_kph: Double? = null,
    val gust_mph: Double? = null,
    val humidity: Int? = null,
    val is_day: Int? = null,
    val last_updated: String? = null,
    val last_updated_epoch: Int? = null,
    val precip_in: Double? = null,
    val precip_mm: Double? = null,
    val pressure_in: Double? = null,
    val pressure_mb: Double? = null,
    val temp_c: Double? = null,
    val temp_f: Double? = null,
    val uv: Double? = null,
    val vis_km: Double? = null,
    val vis_miles: Double? = null,
    val wind_degree: Int? = null,
    val wind_dir: String? = null,
    val wind_kph: Double? = null,
    val wind_mph: Double? = null,
    val code: Int? = null,
    val icon: String? = null,
    val text: String? = null

): Parcelable
