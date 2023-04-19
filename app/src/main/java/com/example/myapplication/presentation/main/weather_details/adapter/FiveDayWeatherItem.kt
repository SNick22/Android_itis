package com.example.myapplication.presentation.main.weather_details.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemWeatherBinding
import com.example.myapplication.domain.weather.forecast.OneDayWeatherInfo

class FiveDayWeatherItem(
    private val binding: ItemWeatherBinding,
    private val parent: ViewGroup
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(weatherInfo: OneDayWeatherInfo) {
        val temp = weatherInfo.temp.toString() + "°"
        binding.run {
            tvWeekDay.text = weatherInfo.date
            tvTemp.text = temp

            Glide
                .with(parent.context)
                .load("https://openweathermap.org/img/wn/${weatherInfo.icon}.png")
                .into(ivWeather)
        }
    }
}