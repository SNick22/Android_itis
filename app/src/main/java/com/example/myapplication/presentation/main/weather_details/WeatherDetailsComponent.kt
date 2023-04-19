package com.example.myapplication.presentation.main.weather_details

import com.example.myapplication.presentation.main.weather.WeatherComponent
import dagger.Subcomponent

@Subcomponent
interface WeatherDetailsComponent {

    fun inject(fragment: WeatherDetailsFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): WeatherDetailsComponent
    }
}