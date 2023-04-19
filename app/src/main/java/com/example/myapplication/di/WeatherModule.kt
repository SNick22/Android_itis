package com.example.myapplication.di

import com.example.myapplication.data.weather.forecast.FiveDayWeatherRepositoryImpl
import com.example.myapplication.data.weather.current.WeatherRepositoryImpl
import com.example.myapplication.data.weather.forecast.datasource.remote.FiveDayWeatherApi
import com.example.myapplication.data.weather.current.datasource.remote.WeatherApi
import com.example.myapplication.domain.weather.forecast.FiveDayWeatherRepository
import com.example.myapplication.domain.weather.current.GetWeatherByCityNameUseCase
import com.example.myapplication.domain.weather.current.GetWeatherByLocationUseCase
import com.example.myapplication.domain.weather.current.WeatherRepository
import com.example.myapplication.domain.weather.forecast.GetFiveDayWeatherUseCase
import dagger.Module
import dagger.Provides

@Module
class WeatherModule {

    @Provides
    fun provideWeatherRepository(weatherApi: WeatherApi): WeatherRepository =
        WeatherRepositoryImpl(weatherApi)

    @Provides
    fun provideFiveDayWeatherRepository(@FiveDayWeatherQualifier fiveDayWeatherApi: FiveDayWeatherApi): FiveDayWeatherRepository =
        FiveDayWeatherRepositoryImpl(fiveDayWeatherApi)

    @Provides
    fun provideWeatherByCityNameUseCase(weatherRepository: WeatherRepository): GetWeatherByCityNameUseCase =
        GetWeatherByCityNameUseCase(weatherRepository)

    @Provides
    fun provideWeatherByLocationUseCase(weatherRepository: WeatherRepository): GetWeatherByLocationUseCase =
        GetWeatherByLocationUseCase(weatherRepository)

    @Provides
    fun provideFiveDayWeatherUseCase(fiveDayWeatherRepository: FiveDayWeatherRepository): GetFiveDayWeatherUseCase =
        GetFiveDayWeatherUseCase(fiveDayWeatherRepository)
}