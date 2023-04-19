package com.example.myapplication.data.weather.current.datasource.remote.response


import com.google.gson.annotations.SerializedName

data class WeatherResponce(
    @SerializedName("main")
    val main: Main?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("weather")
    val weather: List<Weather?>?,
    @SerializedName("wind")
    val wind: Wind?,
    @SerializedName("coord")
    val coord: Coord?
)

data class Wind(
    @SerializedName("speed")
    val speed: Double?
)

data class Weather(
    @SerializedName("icon")
    val icon: String?,
)

data class Main(
    @SerializedName("humidity")
    val humidity: Int?,
    @SerializedName("pressure")
    val pressure: Int?,
    @SerializedName("temp")
    val temp: Double?,
)

data class Coord(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?
)