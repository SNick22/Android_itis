package com.example.myapplication.presentation.main.weather_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.weather.forecast.FiveDayWeatherInfo
import com.example.myapplication.domain.weather.forecast.GetFiveDayWeatherUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class WeatherDetailsViewModel @AssistedInject constructor(
    private val getFiveDayWeatherUseCase: GetFiveDayWeatherUseCase,
    @Assisted(CITY_KEY) private val cityVal: String,
    @Assisted(TEMPERATURE_KEY) private val temperatureVal: Double,
    @Assisted(HUMIDITY_KEY) private val humidityVal: Int,
    @Assisted(PRESSURE_KEY) private val pressureVal: Int,
    @Assisted(SPEED_KEY) private val speedVal: Double,
    @Assisted(ICON_KEY) private val iconVal: String?,
    @Assisted(LAT_KEY) private val lat: Double,
    @Assisted(LON_KEY) private val lon: Double,
) : ViewModel() {

    private val _city = MutableLiveData(cityVal)
    val city: LiveData<String>
        get() = _city

    private val _temp = MutableLiveData(temperatureVal)
    val temp: LiveData<Double>
        get() = _temp

    private val _hum = MutableLiveData(humidityVal)
    val hum: LiveData<Int>
        get() = _hum

    private val _press = MutableLiveData(pressureVal)
    val press: LiveData<Int>
        get() = _press

    private val _speed = MutableLiveData(speedVal)
    val speed: LiveData<Double>
        get() = _speed

    private val _icon = MutableLiveData<String?>(iconVal)
    val icon: LiveData<String?>
        get() = _icon

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _fiveDayWeatherInfo = MutableLiveData<FiveDayWeatherInfo?>(null)
    val fiveDayWeatherInfo: LiveData<FiveDayWeatherInfo?>
        get() = _fiveDayWeatherInfo

    fun getFiveDayWeather() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val data = getFiveDayWeatherUseCase(lat, lon)
                _fiveDayWeatherInfo.value = data
            } catch (error: Throwable) {
                println(error.message)
            } finally {
                _loading.value = false
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(CITY_KEY) city: String,
            @Assisted(TEMPERATURE_KEY) temperature: Double,
            @Assisted(HUMIDITY_KEY) humidity: Int,
            @Assisted(PRESSURE_KEY) pressure: Int,
            @Assisted(SPEED_KEY) speed: Double,
            @Assisted(ICON_KEY) icon: String?,
            @Assisted(LAT_KEY) lat: Double,
            @Assisted(LON_KEY) lon: Double
        ): WeatherDetailsViewModel
    }

    companion object {
        const val CITY_KEY = "city"
        const val TEMPERATURE_KEY = "temperature"
        const val HUMIDITY_KEY = "humidity"
        const val PRESSURE_KEY = "pressure"
        const val SPEED_KEY = "speed"
        const val ICON_KEY = "icon"
        const val LAT_KEY = "lat"
        const val LON_KEY = "lon"
    }
}