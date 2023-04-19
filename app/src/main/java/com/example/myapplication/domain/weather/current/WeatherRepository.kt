package com.example.myapplication.domain.weather.current

interface WeatherRepository {

    suspend fun getWeatherByCityName(city: String): WeatherInfo

    suspend fun getWeatherByLocation(latitude: Double, longitude: Double): WeatherInfo
}