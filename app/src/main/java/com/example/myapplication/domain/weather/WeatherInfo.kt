package com.example.myapplication.domain.weather

data class WeatherInfo(
    val city: String,
    val temperature: Double,
    val humidity: Int,
    val pressure: Int,
    val speed: Double,
    val icon: String?,
)
