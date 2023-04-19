package com.example.myapplication.presentation.main.weather

import dagger.Subcomponent

@Subcomponent
interface WeatherComponent {

    fun inject(fragment: WeatherFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): WeatherComponent
    }
}