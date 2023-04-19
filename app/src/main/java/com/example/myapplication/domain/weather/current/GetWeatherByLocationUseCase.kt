package com.example.myapplication.domain.weather.current

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherByLocationUseCase(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ) = withContext(Dispatchers.IO) {
        repository.getWeatherByLocation(latitude, longitude)
    }
}