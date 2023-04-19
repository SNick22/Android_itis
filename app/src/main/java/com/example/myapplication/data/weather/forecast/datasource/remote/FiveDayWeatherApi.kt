package com.example.myapplication.data.weather.forecast.datasource.remote

import com.example.myapplication.data.weather.forecast.datasource.remote.response.FiveDayWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FiveDayWeatherApi {

    @GET("forecast")
    suspend fun getFiveDayWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): FiveDayWeatherResponse
}