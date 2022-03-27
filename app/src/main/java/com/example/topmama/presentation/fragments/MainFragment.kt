package com.example.topmama.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topmama.data.models.FavouriteRoomWeather
import com.example.topmama.data.models.RoomWeather
import com.example.topmama.domain.models.Weather
import com.example.topmama.presentation.adapters.MainViewAdapter
import com.example.topmama.presentation.viewmodels.MainFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import com.example.topmama.R
import com.example.topmama.domain.models.City
import java.util.*
import kotlin.math.log


@AndroidEntryPoint
@FragmentScoped
class MainFragment : Fragment(R.layout.fragment_main), MainViewAdapter.CallBack {

    private val TAG = "MainFragment"
    private val viewModel: MainFragViewModel by viewModels()
    private var weatherAdapter: MainViewAdapter? = null
    var weatherList: List<RoomWeather>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cities = listOf(
            City(
                0,
                "Nairobi",
                ""
            ),

            City(
                1,
                "Mombasa",
                ""
            ),

            City(
                2,
                "Kisumu",
                ""
            ),

            City(
                3,
                "Nakuru",
                ""
            ),

            City(
                4,
                "Bungoma",
                ""
            ),

            City(
                5,
                "Singapore",
                ""
            ),

            City(
                6,
                "New York",
                ""
            ),

            City(
                7,
                "Kakamega",
                ""
            ),

            City(
                8,
                "Mumbai",
                ""
            ),

            City(
                9,
                "Miami",
                ""
            ),

            City(
                10,
                "Barcelona",
                ""
            ),

            City(
                11,
                "Busia",
                ""
            ),

            City(
                12,
                "Machakos",
                ""
            ),

            City(
                13,
                "Voi",
                ""
            ),

            City(
                14,
                "Marsabit",
                ""
            ),

            City(
                15,
                "Warsaw",
                ""
            ),

            City(
                16,
                "Madrid",
                ""
            ),

            City(
                17,
                "Dubai",
                ""
            ),

            City(
                18,
                "London",
                ""
            ),

            City(
                19,
                "Paris",
                ""
            ),

            City(
                20,
                "Rome",
                ""
            )
        )

        cities.forEach { city ->
            cities.size
            getWeatherData(city.key, "4541ef963d0445cf9a7142701222603", city.name, "yes")
        }

        searchWeather.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                cardView.isVisible = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Toast.makeText(context, "$p0", Toast.LENGTH_SHORT).show()

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length!! <= 0) {
                    cardView.visibility = View.GONE
                }
            }

        })

        searchWeather.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text = v.text.toString()
                searchWeatherData(text)

            }
            false
        }

    }

    private fun mainSwap(weatherList: List<RoomWeather>, cityWeather: RoomWeather) {
        val index = weatherList.indexOf(cityWeather)
        Collections.swap(weatherList, 0, index)
        readAllData(weatherList)
    }

    private fun getWeatherData(cityKey: Int, apiKey: String, cityName: String, aqi: String) {
        lifecycleScope.launch {
            viewModel.getWeatherData(apiKey, cityName, aqi)

            viewModel.weatherData.observe(viewLifecycleOwner, { weather ->
                run {
                    Log.e(TAG, "getWeatherData: ${weather.location.name}")
//                    addWeather(cityKey, weather)
                }
            })
        }
        viewModel.readAllData.observe(viewLifecycleOwner, { weather ->
            weatherList = weather
            readAllData(weatherList!!)
        })
    }

    private fun searchWeatherData(cityName: String) {
        val roomWeather = weatherList?.find { it -> it.name == cityName }
        cardView.visibility = View.VISIBLE
        tvHeader.text = "It's $cityName"
        temp.text = "${roomWeather?.temp_c} ℃"
        humidity.text = roomWeather?.humidity.toString()
        windDirection.text = roomWeather?.wind_dir
        windSpeed.text = roomWeather?.wind_kph.toString()

    }

    private fun readAllData(weatherList: List<RoomWeather>) {
        weatherAdapter = MainViewAdapter(weatherList, this)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvCitiesWeather.layoutManager = linearLayoutManager
        rvCitiesWeather.adapter = weatherAdapter

    }

    override fun moreDetails(weatherItems: RoomWeather) {
        val bundle = Bundle()
        bundle.putParcelable("SingleView", weatherItems)
        Navigation.findNavController(requireView()).navigate(R.id.detailsFragment, bundle)
    }

    override fun addToFavourite(weather: RoomWeather, isFavourite: Boolean) {

        val favouriteRoomWeather = RoomWeather(
            0,
            weather.country,
            weather.lat,
            weather.localtime,
            weather.localtime_epoch,
            weather.lon,
            weather.name,
            weather.region,
            weather.tz_id,
            weather.cloud,
            weather.feelslike_c,
            weather.feelslike_f,
            weather.gust_kph,
            weather.gust_mph,
            weather.humidity,
            weather.is_day,
            weather.last_updated,
            weather.last_updated_epoch,
            weather.precip_in,
            weather.precip_mm,
            weather.pressure_in,
            weather.pressure_mb,
            weather.temp_c,
            weather.temp_f,
            weather.uv,
            weather.vis_km,
            weather.vis_miles,
            weather.wind_degree,
            weather.wind_dir,
            weather.wind_kph,
            weather.wind_mph,
            weather.code,
            weather.icon,
            weather.text
        )

        if (!isFavourite) {
            viewModel.updateWithFavourite(favouriteRoomWeather)
            Toast.makeText(
                context,
                "City added to your favourite and is at the top of the list",
                Toast.LENGTH_SHORT
            ).show()
            mainSwap(weatherList!!, weather)
        } else {
            Toast.makeText(context, "Item already added to your favourites", Toast.LENGTH_SHORT)
                .show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val inflater: MenuInflater = inflater
        return inflater.inflate(R.menu.options, menu)
    }

}