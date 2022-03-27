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
import com.example.topmama.R
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
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.example.topmama.domain.models.City
import kotlin.math.log


@AndroidEntryPoint
@FragmentScoped
class MainFragment : Fragment(R.layout.fragment_main), MainViewAdapter.CallBack {

    private val TAG = "MainFragment"
    private val viewModel: MainFragViewModel by viewModels()
    private var weatherAdapter: MainViewAdapter? = null

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

        searchWeather.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                cardView.isVisible = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Toast.makeText(context, "$p0", Toast.LENGTH_SHORT).show()

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length!! <= 0){
                    cardView.visibility = View.GONE
                }
            }

        })

        searchWeather.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text = v.text.toString()
                searchWeatherData(1222, "4541ef963d0445cf9a7142701222603", text, "yes")
            }
            false
        }

    }

    private fun getWeatherData(cityKey: Int, apiKey: String, cityName: String, aqi: String) {
        lifecycleScope.launch {
            viewModel.getWeatherData(apiKey, cityName, aqi)

            viewModel.weatherData.observe(viewLifecycleOwner, { weather ->
                run {
                    Log.e(TAG, "getWeatherData: ${weather.location.name}")
//                    addWeather(cityKey, weather)
//                    cardView.visibility = View.VISIBLE
                }
            })
        }
        readAllData()
    }

    private fun searchWeatherData(cityKey: Int, apiKey: String, cityName: String, aqi: String) {
        lifecycleScope.launch {
            viewModel.getWeatherData(apiKey, cityName, aqi)

            viewModel.weatherData.observe(viewLifecycleOwner, { weather ->
                run {
                    cardView.visibility = View.VISIBLE
                    tvHeader.text = "It's $cityName"
                    temp.text = "${weather.current.temp_c} ℃"
                    humidity.text = weather.current.humidity.toString()
                    windDirection.text = weather.current.wind_dir
                    windSpeed.text = weather.current.wind_kph.toString()
                }
            })
        }
    }

    private fun addWeather(cityKey: Int, weather: Weather) {
        val roomWeather = RoomWeather(
            cityKey,
            weather.location.country,
            weather.location.lat,
            weather.location.localtime,
            weather.location.localtime_epoch,
            weather.location.lon,
            weather.location.name,
            weather.location.region,
            weather.location.tz_id,
            weather.current.cloud,
            weather.current.feelslike_c,
            weather.current.feelslike_f,
            weather.current.gust_kph,
            weather.current.gust_mph,
            weather.current.humidity,
            weather.current.is_day,
            weather.current.last_updated,
            weather.current.last_updated_epoch,
            weather.current.precip_in,
            weather.current.precip_mm,
            weather.current.pressure_in,
            weather.current.pressure_mb,
            weather.current.temp_c,
            weather.current.temp_f,
            weather.current.uv,
            weather.current.vis_km,
            weather.current.vis_miles,
            weather.current.wind_degree,
            weather.current.wind_dir,
            weather.current.wind_kph,
            weather.current.wind_mph,
            weather.current.condition.code,
            weather.current.condition.icon,
            weather.current.condition.text
        )
        viewModel.addingWeather(roomWeather)

    }

    private fun readAllData() {
        viewModel.readAllData?.observe(viewLifecycleOwner, { weather ->
            weatherAdapter = MainViewAdapter(weather, this)
            val linearLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvCitiesWeather.layoutManager = linearLayoutManager
            rvCitiesWeather.adapter = weatherAdapter

            Log.d(TAG, "readAllData: $weather")
        })

    }

    override fun moreDetails(weatherItems: RoomWeather) {
        val bundle = Bundle()
        bundle.putParcelable("SingleView", weatherItems)
        Navigation.findNavController(requireView()).navigate(R.id.detailsFragment, bundle)
    }

    override fun addToFavourite(weather: RoomWeather) {
//        var isFavourite: Boolean = readState();
//        if (!isFavourite) {
//            favouriteToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context!!,R.drawable.ic_favorite_black_24dp));
//            isFavourite = true;
//            saveState(isFavourite);
//        }
//        else {
//            favouriteToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context!!,R.drawable.ic_favorite_border_black_24dp));
//            isFavourite = false;
//            saveState(isFavourite);
//        }

        val favouriteRoomWeather = FavouriteRoomWeather(
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

        viewModel.addingWeatherFavourite(favouriteRoomWeather)
    }

//    private fun saveState(isFavourite: Boolean) {
//        val aSharedPreferences: SharedPreferences = this.getSharedPreferences("Favourite", Context.MODE_PRIVATE)
//        val aSharedPreferencesEdit = aSharedPreferences.edit()
//        aSharedPreferencesEdit.putBoolean("State", isFavourite)
//        aSharedPreferencesEdit.apply()
//    }
//
//    private fun readState(): Boolean {
//        val asharedPreferences: SharedPreferences = this.getSharedPreferences("Favourite", Context.MODE_PRIVATE)
//        return asharedPreferences.getBoolean("State", false)
//    }

}