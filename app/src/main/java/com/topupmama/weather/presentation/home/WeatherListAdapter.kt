package com.topupmama.weather.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.topupmama.weather.data.model.WeatherData
import com.topupmama.weather.databinding.WeatherItemBinding

class WeatherListAdapter(private val weatherItemClickListener: WeatherItemClickListener, private val favListener: FavListener) :
    ListAdapter<WeatherData, WeatherListAdapter.WeatherViewHolder>(WeatherItemDiffCallback()) {


    //click listener
    class WeatherItemClickListener(val listener: (weatherData:WeatherData) -> Unit){
        fun onClick(weatherData: WeatherData) = listener(weatherData)
    }

    class FavListener(val listener: (cityId:Long) -> Unit){
        fun onClick(cityId:Long) = listener(cityId)
    }

    // diff util callback
    class WeatherItemDiffCallback : DiffUtil.ItemCallback<WeatherData>(){
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = getItem(position) as WeatherData
        Log.d("TAG", "onBindViewHolder: $item")
        holder.bind(weatherData = item, weatherItemClickListener, favListener)
    }




    class WeatherViewHolder
        private constructor(private val binding:WeatherItemBinding): RecyclerView.ViewHolder(binding.root){

            fun bind(weatherData: WeatherData, clickListener: WeatherItemClickListener, favListener: FavListener){
                binding.weatherData = weatherData
                binding.clickListener = clickListener
                binding.favListener = favListener
                binding.executePendingBindings()
            }

            companion object{
                fun from(parent: ViewGroup) : WeatherViewHolder{
                    val inflater = LayoutInflater.from(parent.context)
                    val binding = WeatherItemBinding.inflate(inflater, parent, false)
                    return WeatherViewHolder(binding)
                }
            }

        }

}