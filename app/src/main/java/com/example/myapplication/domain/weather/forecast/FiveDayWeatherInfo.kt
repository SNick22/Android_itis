package com.example.myapplication.domain.weather.forecast

data class FiveDayWeatherInfo(
    val list: List<OneDayWeatherInfo?>?,
)

data class OneDayWeatherInfo(
    val date: String,
    val temp: Double,
    val icon: String?,
)
