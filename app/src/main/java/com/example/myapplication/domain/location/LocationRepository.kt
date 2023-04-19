package com.example.myapplication.domain.location

import android.content.Context

interface LocationRepository {

    suspend fun getLocation(): LocationInfo
}