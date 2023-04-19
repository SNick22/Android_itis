package com.example.myapplication.data.weather.current

import com.example.myapplication.data.weather.current.datasource.remote.WeatherApi
import com.example.myapplication.data.weather.current.mapper.toWeatherInfo
import com.example.myapplication.domain.weather.current.WeatherInfo
import com.example.myapplication.domain.weather.current.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherByCityName(
        city: String
    ) : WeatherInfo = api.getWeatherByCityName(city).toWeatherInfo()

    override suspend fun getWeatherByLocation(
        latitude: Double,
        longitude: Double
    ) : WeatherInfo = api.getWeatherByLocation(latitude, longitude).toWeatherInfo()
}