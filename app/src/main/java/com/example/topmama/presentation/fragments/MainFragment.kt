package com.example.topmama.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.topmama.R
import com.example.topmama.presentation.viewmodels.MainFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
@FragmentScoped
class MainFragment : Fragment(R.layout.fragment_main) {

    private val TAG = "MainFragment"
    private val viewModel: MainFragViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getWeatherData()
    }

    private fun getWeatherData() {
        lifecycleScope.launch {
            viewModel.getWeatherData()

            viewModel.weatherData.observe(viewLifecycleOwner, { weather ->
                run {
                    sample.text = weather.location.country
                    // TODO: Create an instance of Room
                    // TODO: Once data is fetched from Webservice save to the local storage and emit to the UI
                    // TODO: Carry on with the UI to completion
                    // TODO: Use mappers to model the data into what I will want to use mainly on the UI
                }
            })
        }
    }

    companion object {

    }
}