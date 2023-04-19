package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.location.LocationRepositoryImpl
import com.example.myapplication.data.location.datasource.remote.LocationDataSource
import com.example.myapplication.domain.location.GetLocationUseCase
import com.example.myapplication.domain.location.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides

@Module
class LocationModule {

    @Provides
    fun provideFusedLocationClient(context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    fun provideLocationDataSource(fusedLocationClient: FusedLocationProviderClient): LocationDataSource =
        LocationDataSource(fusedLocationClient)

    @Provides
    fun provideLocationRepository(locationDataSource: LocationDataSource): LocationRepository =
        LocationRepositoryImpl(locationDataSource)

    @Provides
    fun provideLocationUseCase(locationRepository: LocationRepository): GetLocationUseCase =
        GetLocationUseCase(locationRepository)
}