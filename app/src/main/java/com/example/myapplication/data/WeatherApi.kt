package com.example.myapplication.data

import com.example.myapplication.data.responce.WeatherResponce
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
    ): WeatherResponce

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String = "metric",
    ): WeatherResponce
}