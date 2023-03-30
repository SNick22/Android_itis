package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.core.interceptor.ApiKeyInterceptor
import com.example.myapplication.data.location.LocationRepositoryImpl
import com.example.myapplication.data.location.datasource.remote.LocationDataSource
import com.example.myapplication.data.weather.WeatherRepositoryImpl
import com.example.myapplication.data.weather.datasource.remote.WeatherApi
import com.example.myapplication.domain.location.GetLocationUseCase
import com.example.myapplication.domain.weather.GetWeatherByCityNameUseCase
import com.example.myapplication.domain.weather.GetWeatherByLocationUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataContainer {

    private const val BASE_URL = BuildConfig.API_ENDPOINT

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor())
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideContext(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private val locationDataSource
        get() = LocationDataSource(fusedLocationClient
            ?: throw Exception("fusedLocationClient is null")
        )

    private val locationRepository
        get() = LocationRepositoryImpl(locationDataSource)

    private val weatherApi = retrofit.create(WeatherApi::class.java)

    private val weatherRepository = WeatherRepositoryImpl(weatherApi)

    val weatherByCityNameUseCase: GetWeatherByCityNameUseCase
        get() = GetWeatherByCityNameUseCase(weatherRepository)

    val weatherByLocationUseCase: GetWeatherByLocationUseCase
        get() = GetWeatherByLocationUseCase(weatherRepository)

    val locationUseCase: GetLocationUseCase
        get() = GetLocationUseCase(locationRepository)
}