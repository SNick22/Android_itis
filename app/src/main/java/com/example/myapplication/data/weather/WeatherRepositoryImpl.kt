package com.example.myapplication.data.weather

import com.example.myapplication.data.weather.datasource.remote.WeatherApi
import com.example.myapplication.data.weather.mapper.toWeatherInfo
import com.example.myapplication.domain.weather.WeatherInfo
import com.example.myapplication.domain.weather.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherByCityName(
        city: String
    ) : WeatherInfo = api.getWeatherByCityName(city).toWeatherInfo()

    override suspend fun getWeatherByLocation(
        latitude: String,
        longitude: String
    ) : WeatherInfo = api.getWeatherByLocation(latitude, longitude).toWeatherInfo()
}