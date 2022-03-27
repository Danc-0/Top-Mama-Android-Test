package com.example.topmama.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.topmama.R
import com.example.topmama.data.models.RoomWeather
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details,
            container,
            false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherData = arguments
        val weather: RoomWeather = weatherData?.get("SingleView") as RoomWeather

        weatherBackground.load(weather.backgroundImage)
        tvHeader.text = weather.region
        tvHeaderCity.text = weather.region
        temp.text = "${weather.temp_c} ℃"
        windDegree.text = weather.wind_degree.toString()
        windDirection.text = weather.wind_dir
        windSpeed.text = weather.wind_kph.toString()
        humidity.text = weather.humidity.toString()
        clouds.text = weather.cloud.toString()
        visbility.text = weather.vis_km.toString()
        precipitation.text = weather.precip_in.toString()
        pressure.text = weather.pressure_in.toString()
        feelsLike.text = weather.feelslike_c.toString()

    }
}