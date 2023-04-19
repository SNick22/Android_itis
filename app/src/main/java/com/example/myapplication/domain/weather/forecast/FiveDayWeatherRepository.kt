package com.example.myapplication.domain.weather.forecast

interface FiveDayWeatherRepository {

    suspend fun getFiveDayWeather(latitude: Double, longitude: Double): FiveDayWeatherInfo
}