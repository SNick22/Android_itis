package com.example.myapplication.data.weather.current.datasource.remote

import com.example.myapplication.data.weather.current.datasource.remote.response.WeatherResponce
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") city: String,
    ): WeatherResponce

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): WeatherResponce
}