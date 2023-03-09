package com.example.myapplication.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.DataContainer
import com.example.myapplication.data.responce.WeatherResponce
import com.example.myapplication.databinding.FragmentFirstBinding
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch


class FirstFragment : Fragment(R.layout.fragment_first) {

    private var binding: FragmentFirstBinding? = null

    private val api = DataContainer.weatherApi

    private var data: WeatherResponce? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)

        binding?.run {
            btnCoordSearch.setOnClickListener {
                getByLocation()
            }
            btnCitySearch.setOnClickListener {
                val cityName = etCity.text.toString()
                getWeather(cityName=cityName)
            }
            tvCity.setOnClickListener {
                data?.run {
                    val bundle = Bundle()
                    bundle.putString("city", name)
                    bundle.putDouble("temperature", main!!.temp!!)
                    bundle.putInt("humidity", main.humidity!!)
                    bundle.putInt("pressure", main.pressure!!)
                    bundle.putDouble("speed", wind!!.speed!!)
                    bundle.putString("icon", weather!![0]!!.icon)

                    BottomSheetFragment.newInstance(bundle).show(parentFragmentManager, null)
                }
            }
        }
    }

    private fun getWeather(
        byLocation: Boolean = false,
        latitude: String = "",
        longitude: String = "",
        cityName: String = ""
    ) {
        lifecycleScope.launch {
            try {
                showLoader(true)
                val weatherResponce = if (byLocation) {
                    api.getWeatherByLocation(latitude, longitude)
                } else {
                    api.getWeatherByCityName(cityName)
                }

                binding?.run {
                    tvCity.text = weatherResponce.name
                    val temperature = weatherResponce.main?.temp.toString() + " °C"
                    tvTemp.text = temperature
                }

                data = weatherResponce
            } catch (error: Throwable) {
                Toast.makeText(activity, "Error receiving data", Toast.LENGTH_SHORT)
                    .show()
            } finally {
                showLoader(false)
            }
        }
    }

    private fun showLoader(isShow: Boolean) {
        binding?.run {
            tvCity.isVisible = !isShow
            tvTemp.isVisible = !isShow
            pbLoader.isVisible = isShow
        }
    }

    private fun checkLocationPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            1
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    private fun getByLocation() {
        if (checkLocationPermissions()) {
            if (isLocationEnabled()) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    getWeather(
                        true,
                        location.latitude.toString(),
                        location.longitude.toString(),
                    )
                }
            } else {
                Toast.makeText(requireContext(), "Please turn on the gps", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            requestLocationPermissions()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}