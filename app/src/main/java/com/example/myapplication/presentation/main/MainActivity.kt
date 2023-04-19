package com.example.myapplication.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presentation.main.weather.WeatherFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val containerId: Int = R.id.container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            return
        }

        supportFragmentManager.beginTransaction()
            .replace(containerId, WeatherFragment.getInstance(), WeatherFragment.WEATHER_FRAGMENT_TAG)
            .commit()
    }
}