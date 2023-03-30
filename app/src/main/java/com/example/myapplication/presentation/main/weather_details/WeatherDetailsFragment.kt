package com.example.myapplication.presentation.main.weather_details

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WeatherDetailsFragment: BottomSheetDialogFragment(R.layout.bottom_sheet) {

    private var binding: BottomSheetBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BottomSheetBinding.bind(view)

        binding?.run {
            arguments?.run {
                tvCityName.text = getString("city")
                tvTemperatureData.text = getDouble("temperature").toString()
                tvAirHumidityData.text = getInt("humidity").toString()
                tvAirPressureData.text = getInt("pressure").toString()
                tvWindSpeedData.text = getDouble("speed").toString()

                getString("icon")?.let {
                    Glide
                        .with(requireContext())
                        .load("https://openweathermap.org/img/wn/${it}.png")
                        .into(ivWeather)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(bundle: Bundle) = WeatherDetailsFragment().apply {
            arguments = bundle
        }
    }
}