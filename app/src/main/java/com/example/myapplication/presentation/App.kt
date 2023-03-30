package com.example.myapplication.presentation

import android.app.Application
import com.example.myapplication.di.DataContainer

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        DataContainer.provideContext(this)
    }
}