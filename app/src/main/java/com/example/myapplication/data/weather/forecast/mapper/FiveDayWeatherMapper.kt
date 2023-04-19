package com.example.myapplication.data.weather.forecast.mapper

import com.example.myapplication.data.weather.forecast.datasource.remote.response.FiveDayWeatherResponse
import com.example.myapplication.data.weather.forecast.datasource.remote.response.TimestampWeather
import com.example.myapplication.domain.weather.forecast.FiveDayWeatherInfo
import com.example.myapplication.domain.weather.forecast.OneDayWeatherInfo
import java.util.*

fun FiveDayWeatherResponse.toFiveDayWeatherInfo(): FiveDayWeatherInfo {
    val fiveDayWeatherList = list?.filterIndexed { index, _ ->
        index % 8 == 0
    }

    return FiveDayWeatherInfo(
        list = fiveDayWeatherList?.map {
            it?.toOneDayWeatherInfo()
        }
    )
}

fun TimestampWeather.toOneDayWeatherInfo(): OneDayWeatherInfo = OneDayWeatherInfo(
    date = dt?.let {
        val date = Date(it * 1000L)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        daysOfWeek[currentDayOfWeek]
                   } ?: "Error",
    temp = main?.temp ?: 0.0,
    icon = weather?.firstOrNull()?.icon
)

val daysOfWeek = mapOf(
    Calendar.MONDAY to "Monday",
    Calendar.TUESDAY to "Tuesday",
    Calendar.WEDNESDAY to "Wednesday",
    Calendar.THURSDAY to "Thursday",
    Calendar.FRIDAY to "Friday",
    Calendar.SATURDAY to "Saturday",
    Calendar.SUNDAY to "Sunday",
)