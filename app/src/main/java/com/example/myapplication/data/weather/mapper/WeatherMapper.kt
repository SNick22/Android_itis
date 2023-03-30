package com.example.myapplication.data.weather.mapper

import com.example.myapplication.data.weather.datasource.remote.responce.WeatherResponce
import com.example.myapplication.domain.weather.WeatherInfo

fun WeatherResponce.toWeatherInfo(): WeatherInfo = WeatherInfo(
    city = name ?: "null",
    temperature = main?.temp ?: 0.0,
    humidity = main?.humidity ?: 0,
    pressure = main?.pressure ?: 0,
    speed = wind?.speed ?: 0.0,
    icon = weather?.firstOrNull()?.icon
)