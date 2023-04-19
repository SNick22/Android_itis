package com.example.myapplication.presentation.main.weather_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemWeatherBinding
import com.example.myapplication.domain.weather.forecast.OneDayWeatherInfo

class FiveDayWeatherAdapter(
    private val list: List<OneDayWeatherInfo?>?
) : RecyclerView.Adapter<FiveDayWeatherItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiveDayWeatherItem =
        FiveDayWeatherItem(
            binding = ItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            parent = parent
        )

    override fun onBindViewHolder(holder: FiveDayWeatherItem, position: Int) {
        list?.get(position)?.let {
            holder.onBind(it)
        }
    }

    override fun getItemCount(): Int = list?.size ?: 0
}