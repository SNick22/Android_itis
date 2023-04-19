package com.example.myapplication.presentation

import android.app.Application
import com.example.myapplication.di.*

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(applicationContext)
            .build()
    }
}