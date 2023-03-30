package com.example.myapplication.domain.weather

interface WeatherRepository {

    suspend fun getWeatherByCityName(city: String): WeatherInfo

    suspend fun getWeatherByLocation(latitude: String, longitude: String): WeatherInfo
}