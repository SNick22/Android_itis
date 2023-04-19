package com.example.myapplication.di

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.core.interceptor.ApiKeyInterceptor
import com.example.myapplication.data.weather.forecast.datasource.remote.FiveDayWeatherApi
import com.example.myapplication.data.weather.current.datasource.remote.WeatherApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

private const val BASE_URL = BuildConfig.API_ENDPOINT

@Module
class NetworkModule {

    @Provides
    @Named("logging")
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Named("api_key")
    fun provideApiKeyInterceptor(): Interceptor = ApiKeyInterceptor()

    @Provides
    fun provideHttpClient(
        @Named("logging") loggingInterceptor: Interceptor,
        @Named("api_key") apiKeyInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()

    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideWeatherApi(
        retrofit: Retrofit
    ): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Provides
    @FiveDayWeatherQualifier
    fun provideFiveDayWeatherApi(
        retrofit: Retrofit
    ): FiveDayWeatherApi =
        retrofit.create(FiveDayWeatherApi::class.java)
}