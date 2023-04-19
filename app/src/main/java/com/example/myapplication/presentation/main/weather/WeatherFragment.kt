package com.example.myapplication.presentation.main.weather

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.di.appComponent
import com.example.myapplication.di.lazyViewModel
import com.example.myapplication.presentation.main.MainActivity
import com.example.myapplication.presentation.main.weather_details.WeatherDetailsFragment


class WeatherFragment : Fragment(R.layout.fragment_first) {

    private var binding: FragmentFirstBinding? = null

    private val viewModel: WeatherViewModel by lazyViewModel {
        requireContext().appComponent().weatherViewModel().create()
    }

    override fun onAttach(context: Context) {
        requireContext().appComponent()
            .plusWeatherComponent()
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)

        binding?.run {
            btnCoordSearch.setOnClickListener {
                viewModel.getWeatherByLocation()
            }
            btnCitySearch.setOnClickListener {
                val cityName = etCity.text.toString()
                viewModel.getWeather(cityName=cityName)
            }
            tvCity.setOnClickListener {
                viewModel.navigateToDetails()
            }
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.registerActivityResult(activity as MainActivity)

        with(viewModel) {
            loading.observe(viewLifecycleOwner) {
                showLoader(it)
            }
            weatherInfo.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                binding?.run {
                    tvCity.text = it.city
                    val temperature = it.temperature.toString() + "°"
                    tvTemp.text = temperature
                }
            }
            message.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                Toast.makeText(activity, it, Toast.LENGTH_SHORT)
                    .show()
            }
            navigateDetails.observe(viewLifecycleOwner) {
                if (it) {
                    val bundle = Bundle()
                    weatherInfo.value?.run {
                        bundle.putString("city", city)
                        bundle.putDouble("temperature", temperature)
                        bundle.putInt("humidity", humidity)
                        bundle.putInt("pressure", pressure)
                        bundle.putDouble("speed", speed)
                        bundle.putString("icon", icon)
                        bundle.putDouble("lat", lat)
                        bundle.putDouble("lon", lon)
                    }
                    WeatherDetailsFragment.newInstance(bundle).show(parentFragmentManager, null)
                    navigateDetails.value = false
                }
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val WEATHER_FRAGMENT_TAG = "WEATHER_FRAGMENT_TAG"

        fun getInstance() = WeatherFragment()
    }
}