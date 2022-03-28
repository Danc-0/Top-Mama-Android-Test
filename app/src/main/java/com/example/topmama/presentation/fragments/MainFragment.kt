package com.example.topmama.presentation.fragments

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topmama.data.models.RoomWeather
import com.example.topmama.presentation.adapters.MainViewAdapter
import com.example.topmama.presentation.viewmodels.MainFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.topmama.R
import com.example.topmama.domain.models.City
import com.example.topmama.presentation.MainActivity
import com.example.topmama.utils.notify.NotifyWork
import com.google.android.material.snackbar.Snackbar
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
@FragmentScoped
class MainFragment : Fragment(R.layout.fragment_main), MainViewAdapter.CallBack {

    private val TAG = "MainFragment"
    private val viewModel: MainFragViewModel by viewModels()
    private var weatherAdapter: MainViewAdapter? = null
    var weatherList: List<RoomWeather>? = null
    var searchedCity: City? = null
    lateinit var apiKey: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ai: ApplicationInfo = context?.packageManager!!.getApplicationInfo(context!!.packageName, PackageManager.GET_META_DATA)
        apiKey = ai.metaData["apiKey"].toString()

        val cities = listOf(
            City(
                0,
                "Nairobi",
                "https://images.unsplash.com/photo-1516912481808-3406841bd33c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8d2VhdGhlcnxlbnwwfHwwfHw%3D&w=1000&q=80"
            ),

            City(
                1,
                "Mombasa",
                "https://media.nationalgeographic.org/assets/photos/000/263/26383.jpg"
            ),

            City(
                2,
                "Kisumu",
                "https://upload.wikimedia.org/wikipedia/commons/f/fc/Thunder_lightning_Garajau_Madeira_289985700.jpg"
            ),

            City(
                3,
                "Nakuru",
                "https://cdn.vox-cdn.com/thumbor/ujGWXHHIF3WvAwBWDPPeF8FnBJE=/0x0:4500x3000/1200x675/filters:focal(1890x1140:2610x1860)/cdn.vox-cdn.com/uploads/chorus_image/image/69704257/840653818.0.jpg"
            ),

            City(
                4,
                "Bungoma",
                "https://s.abcnews.com/images/US/Colorado_Weather_200627_hpMain_20200627-063316_4x3_992.jpg"
            ),

            City(
                5,
                "Singapore",
                "https://images.unsplash.com/photo-1516912481808-3406841bd33c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8d2VhdGhlcnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                6,
                "New York",
                "https://images.unsplash.com/photo-1590552515252-3a5a1bce7bed?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80"
            ),

            City(
                7,
                "Kakamega",
                "https://images.unsplash.com/photo-1527482797697-8795b05a13fe?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80"
            ),

            City(
                8,
                "Mumbai",
                "https://images.unsplash.com/photo-1548996206-122c521b2d39?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTl8fHdlYXRoZXJ8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                9,
                "Miami",
                "https://images.unsplash.com/photo-1561484930-998b6a7b22e8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MzJ8fHdlYXRoZXJ8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                10,
                "Barcelona",
                "https://images.unsplash.com/photo-1606297743955-8b2a39b2ddc5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8ODh8fHdlYXRoZXJ8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                11,
                "Busia",
                "https://images.unsplash.com/photo-1591537580018-04f735250c5f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTA0fHx3ZWF0aGVyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                12,
                "Machakos",
                "https://images.unsplash.com/photo-1562510950-b51be406d3d6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTE0fHx3ZWF0aGVyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                13,
                "Voi",
                "https://images.unsplash.com/photo-1581038199836-c9aee2f9c58d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTI1fHx3ZWF0aGVyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                14,
                "Marsabit",
                "https://images.unsplash.com/photo-1604335277506-a980d7b2f453?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTUzfHx3ZWF0aGVyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                15,
                "Warsaw",
                "https://images.unsplash.com/photo-1606485904776-f27da55555ab?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTczfHx3ZWF0aGVyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                16,
                "Madrid",
                "https://images.unsplash.com/photo-1617204072941-f344713fb8ac?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTcyfHx3ZWF0aGVyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                17,
                "Dubai",
                "https://images.unsplash.com/photo-1585046400328-5bcf0c9fba16?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTk4fHx3ZWF0aGVyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                18,
                "London",
                "https://images.unsplash.com/photo-1574301042487-5c6a1bb7ef8a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjAxfHx3ZWF0aGVyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                19,
                "Paris",
                "https://images.unsplash.com/photo-1587117417759-df012039358b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjA0fHx3ZWF0aGVyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
            ),

            City(
                20,
                "Rome",
                "https://images.unsplash.com/photo-1581347457329-2bedd4522af8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjYwfHx3ZWF0aGVyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
            )
        )

        cities.forEach { city ->
            cities.size
            searchedCity = city
            getWeatherData(searchedCity!!,apiKey, city.name, "yes")
        }

        searchWeather.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                cardView.isVisible = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

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
                searchWeatherData(v, text)

            }
            false
        }

    }

    private fun mainSwap(weatherList: List<RoomWeather>, cityWeather: RoomWeather) {
        val index = weatherList.indexOf(cityWeather)
        Collections.swap(weatherList, 0, index)
        readAllData(weatherList)
    }

    private fun getWeatherData(searchedCity: City, apiKey: String, cityName: String, aqi: String) {
        lifecycleScope.launch {
            viewModel.getWeatherData(searchedCity, apiKey, cityName, aqi)

            viewModel.weatherData.observe(viewLifecycleOwner, { weather ->
                run {

                }
            })
        }
        viewModel.readAllData.observe(viewLifecycleOwner, { weather ->
            weatherList = weather
            readAllData(weatherList!!)
        })
    }

    private fun searchWeatherData(view: View, cityName: String) {
        val roomWeather = weatherList?.find { it -> it.name == cityName }
        if (roomWeather != null){
            cardView.visibility = View.VISIBLE
            tvHeader.text = "It's $cityName"
            temp.text = "${roomWeather.temp_c} ℃"
            humidity.text = roomWeather.humidity.toString()
            windDirection.text = roomWeather.wind_dir
            windSpeed.text = roomWeather.wind_kph.toString()
        } else {
            Snackbar.make(view, "$cityName is not available in the list yet", Snackbar.LENGTH_LONG).show()

        }
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun addToFavourite(weather: RoomWeather, isFavourite: Boolean) {
        val favouriteRoomWeather = RoomWeather(
            0,
            weather.backgroundImage,
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
            val data = Data.Builder()
                .putInt(MainActivity.NOTIFICATION_ID, 0)
                .build()
            scheduleNotification(durationTime(), data)
        } else {
            Toast.makeText(context, "Item already added to your favourites", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun scheduleNotification(delay: Long, data: Data) {
        val instanceWorkManager = WorkManager.getInstance(context!!)

        val notificationConstraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicNotificationWork = PeriodicWorkRequestBuilder<NotifyWork>(30, TimeUnit.SECONDS)
            .setConstraints(notificationConstraints)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        instanceWorkManager.enqueue(periodicNotificationWork)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun durationTime(): Long {
        val start: LocalDateTime = LocalDateTime.now()
        val end: LocalDateTime = start.plusHours(1).truncatedTo(ChronoUnit.HOURS)
        val duration: Duration = Duration.between(start, end)
        return duration.toMillis()
    }

}