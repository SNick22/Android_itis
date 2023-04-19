package com.example.myapplication.data.weather.forecast

import com.example.myapplication.data.weather.forecast.datasource.remote.FiveDayWeatherApi
import com.example.myapplication.data.weather.forecast.mapper.toFiveDayWeatherInfo
import com.example.myapplication.domain.weather.forecast.FiveDayWeatherInfo
import com.example.myapplication.domain.weather.forecast.FiveDayWeatherRepository

class FiveDayWeatherRepositoryImpl(
    private val api: FiveDayWeatherApi
) : FiveDayWeatherRepository {

    override suspend fun getFiveDayWeather(
        latitude: Double,
        longitude: Double
    ) : FiveDayWeatherInfo = api.getFiveDayWeather(latitude, longitude).toFiveDayWeatherInfo()
}