package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.presentation.main.weather.WeatherComponent
import com.example.myapplication.presentation.main.weather.WeatherViewModel
import com.example.myapplication.presentation.main.weather_details.WeatherDetailsComponent
import com.example.myapplication.presentation.main.weather_details.WeatherDetailsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        WeatherModule::class,
        LocationModule::class
    ]
)
@Singleton
interface AppComponent {

    fun weatherViewModel(): WeatherViewModel.Factory

    fun weatherDetailsViewModel(): WeatherDetailsViewModel.Factory

    fun plusWeatherComponent(): WeatherComponent.Builder

    fun plusWeatherDetailsComponent(): WeatherDetailsComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(applicationContext: Context): Builder

        fun build(): AppComponent
    }
}