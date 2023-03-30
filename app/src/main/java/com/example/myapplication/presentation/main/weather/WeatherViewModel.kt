package com.example.myapplication.presentation.main.weather

import android.Manifest
import android.content.Context
import android.location.LocationManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication.di.DataContainer
import com.example.myapplication.domain.location.GetLocationUseCase
import com.example.myapplication.domain.weather.GetWeatherByCityNameUseCase
import com.example.myapplication.domain.weather.GetWeatherByLocationUseCase
import com.example.myapplication.domain.weather.WeatherInfo
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val getLocationUseCase: GetLocationUseCase
) : ViewModel() {

    private val _weatherInfo = MutableLiveData<WeatherInfo?>(null)
    val weatherInfo: LiveData<WeatherInfo?>
        get() = _weatherInfo

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _message = MutableLiveData<String?>(null)
    val message: LiveData<String?>
        get() = _message

    val navigateDetails = MutableLiveData(false)

    private var permission: ActivityResultLauncher<String>? = null

    fun registerActivityResult(fragment: WeatherFragment) {
        permission = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it && isLocationEnabled(fragment)) {
                getWeather(true)
            }
        }
    }

    fun getWeatherByLocation() {
        permission?.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    fun getWeather(
        byLocation: Boolean = false,
        cityName: String = ""
    ) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val weatherInfo = if (byLocation) {
                    getLocationUseCase().run {
                        getWeatherByLocationUseCase(latitude, longitude)
                    }
                } else {
                    getWeatherByCityNameUseCase(cityName)
                }

                _weatherInfo.value = weatherInfo
            } catch (error: Throwable) {
                _message.value = "Error receiving data"
            } finally {
                _loading.value = false
            }
        }
    }

    fun navigateToDetails() {
        navigateDetails.value = true
    }

    private fun isLocationEnabled(fragment: WeatherFragment): Boolean {
        val locationManager: LocationManager = fragment.context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!isEnabled) {
            _message.value = "Please turn on location"
        }
        return isEnabled
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val getWeatherByCityNameUseCase = DataContainer.weatherByCityNameUseCase
                val getWeatherByLocationUseCase = DataContainer.weatherByLocationUseCase
                val getLocationUseCase = DataContainer.locationUseCase
                return WeatherViewModel(
                    getWeatherByCityNameUseCase,
                    getWeatherByLocationUseCase,
                    getLocationUseCase
                ) as T
            }
        }
    }
}