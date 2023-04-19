package com.example.myapplication.presentation.main.weather_details

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.BottomSheetBinding
import com.example.myapplication.di.appComponent
import com.example.myapplication.di.lazyViewModel
import com.example.myapplication.presentation.main.weather_details.adapter.FiveDayWeatherAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WeatherDetailsFragment: BottomSheetDialogFragment(R.layout.bottom_sheet) {

    private var binding: BottomSheetBinding? = null

    private lateinit var viewModel: Lazy<WeatherDetailsViewModel>

    private lateinit var adapter: FiveDayWeatherAdapter

    override fun onAttach(context: Context) {
        requireContext().appComponent()
            .plusWeatherDetailsComponent()
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val city = arguments?.getString("city") ?: ""
        val temperature = arguments?.getDouble("temperature") ?: 0.0
        val humidity = arguments?.getInt("humidity") ?: 0
        val pressure = arguments?.getInt("pressure") ?: 0
        val speed = arguments?.getDouble("speed") ?: 0.0
        val icon = arguments?.getString("icon")
        val lat = arguments?.getDouble("lat") ?: 0.0
        val lon = arguments?.getDouble("lon") ?: 0.0

        viewModel = lazyViewModel {
            requireContext().appComponent().weatherDetailsViewModel().create(
                city, temperature, humidity, pressure, speed, icon, lat, lon
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BottomSheetBinding.bind(view)

        viewModel.value.getFiveDayWeather()

        observeViewModel()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.peekHeight = it.height
            }
        }

        return dialog
    }

    private fun observeViewModel() {
        with(viewModel.value) {
            binding?.run {
                city.observe(viewLifecycleOwner) {
                    tvCityName.text = it
                }
                temp.observe(viewLifecycleOwner) {
                    tvTemperatureData.text = it.toString()
                }
                hum.observe(viewLifecycleOwner) {
                    tvAirHumidityData.text = it.toString()
                }
                press.observe(viewLifecycleOwner) {
                    tvAirPressureData.text = it.toString()
                }
                speed.observe(viewLifecycleOwner) {
                    tvWindSpeedData.text = it.toString()
                }
                icon.observe(viewLifecycleOwner) {
                    if (it != null) {
                        Glide
                            .with(requireContext())
                            .load("https://openweathermap.org/img/wn/${it}.png")
                            .into(ivWeather)
                    }
                }
                fiveDayWeatherInfo.observe(viewLifecycleOwner) {
                    if (it != null) {
                        adapter = FiveDayWeatherAdapter(it.list)
                        rvFiveDayWeather.adapter = adapter
                        rvFiveDayWeather.layoutManager = LinearLayoutManager(
                            context,
                            LinearLayoutManager.HORIZONTAL,
                            false)
                    }
                }
                loading.observe(viewLifecycleOwner) {
                    showLoader(it)
                }
            }
        }
    }

    private fun showLoader(isShow: Boolean) {
        binding?.run {
            if (isShow) {
                pbLoader.visibility = View.VISIBLE
            } else {
                pbLoader.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(bundle: Bundle) = WeatherDetailsFragment().apply {
            arguments = bundle
        }
    }
}