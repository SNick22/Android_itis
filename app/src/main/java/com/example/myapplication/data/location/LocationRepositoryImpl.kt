package com.example.myapplication.data.location

import android.content.Context
import com.example.myapplication.data.location.datasource.remote.LocationDataSource
import com.example.myapplication.domain.location.LocationInfo
import com.example.myapplication.domain.location.LocationRepository

class LocationRepositoryImpl(
    private val locationDataSource: LocationDataSource
): LocationRepository {

    override suspend fun getLocation(): LocationInfo = locationDataSource.getLocation()
}