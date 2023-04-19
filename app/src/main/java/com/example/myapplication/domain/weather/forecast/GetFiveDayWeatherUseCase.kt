package com.example.myapplication.domain.weather.forecast

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFiveDayWeatherUseCase(
    private val repository: FiveDayWeatherRepository
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ) = withContext(Dispatchers.IO) {
        repository.getFiveDayWeather(latitude, longitude)
    }
}