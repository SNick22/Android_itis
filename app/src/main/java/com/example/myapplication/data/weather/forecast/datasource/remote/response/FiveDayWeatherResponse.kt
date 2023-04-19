package com.example.myapplication.data.weather.forecast.datasource.remote.response


import com.google.gson.annotations.SerializedName

data class FiveDayWeatherResponse(
    @SerializedName("list")
    val list: List<TimestampWeather?>?,
)

data class TimestampWeather(
    @SerializedName("dt")
    val dt: Int?,
    @SerializedName("main")
    val main: Main?,
    @SerializedName("weather")
    val weather: List<Weather?>?,
)

data class Weather(
    @SerializedName("icon")
    val icon: String?,
)

data class Main(
    @SerializedName("temp")
    val temp: Double?,
)