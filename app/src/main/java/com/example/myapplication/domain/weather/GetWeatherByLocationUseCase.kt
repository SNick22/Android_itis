package com.example.myapplication.domain.weather

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherByLocationUseCase(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(
        latitude: String,
        longitude: String
    ) = withContext(Dispatchers.IO) {
        repository.getWeatherByLocation(latitude, longitude)
    }
}