package com.example.myapplication.data.weather.current.mapper

import com.example.myapplication.data.weather.current.datasource.remote.response.WeatherResponce
import com.example.myapplication.domain.weather.current.WeatherInfo

fun WeatherResponce.toWeatherInfo(): WeatherInfo = WeatherInfo(
    city = name ?: "null",
    temperature = main?.temp ?: 0.0,
    humidity = main?.humidity ?: 0,
    pressure = main?.pressure ?: 0,
    speed = wind?.speed ?: 0.0,
    icon = weather?.firstOrNull()?.icon,
    lat = coord?.lat ?: 0.0,
    lon = coord?.lon ?: 0.0
)