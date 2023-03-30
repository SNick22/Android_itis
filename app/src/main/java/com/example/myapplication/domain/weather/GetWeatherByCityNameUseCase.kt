package com.example.myapplication.domain.weather

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherByCityNameUseCase(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(
        city: String
    ) = withContext(Dispatchers.IO) {
        repository.getWeatherByCityName(city)
    }
}