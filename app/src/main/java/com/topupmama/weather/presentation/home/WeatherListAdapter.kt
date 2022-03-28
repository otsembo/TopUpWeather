package com.topupmama.weather.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.topupmama.weather.common.AppConstants.TRANSITION_NAME
import com.topupmama.weather.data.model.WeatherData
import com.topupmama.weather.databinding.WeatherItemBinding

class WeatherListAdapter(private val weatherItemClickListener: WeatherItemClickListener, private val favListener: FavListener) :
    ListAdapter<WeatherData, WeatherListAdapter.WeatherViewHolder>(WeatherItemDiffCallback()), Filterable {

    private val originalList:List<WeatherData> = currentList

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
        holder.bind(weatherData = item, weatherItemClickListener, favListener)
    }




    class WeatherViewHolder
        private constructor(private val binding:WeatherItemBinding): RecyclerView.ViewHolder(binding.root){

            fun bind(weatherData: WeatherData, clickListener: WeatherItemClickListener, favListener: FavListener){
                binding.weatherData = weatherData
                binding.clickListener = clickListener
                binding.favListener = favListener
                binding.weatherLogo.transitionName = "transition${weatherData.id}"
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

    override fun getFilter(): Filter {
       return object : Filter() {

           val filteredList = mutableListOf<WeatherData>()

           override fun performFiltering(p0: CharSequence?): FilterResults {


               p0?.let {

                   if(it.isEmpty()) {
                       submitList(originalList)
                   }
                   else{

                       val pattern = it.toString().lowercase().trim()
                       currentList.forEach { weatherData ->
                           if(weatherData.city.lowercase().contains(pattern)){

                               filteredList.add(weatherData)
                           }
                       }

                       Log.d("TAG", "performFiltering: ${filteredList.size}")

                       submitList(filteredList)

                   }

               }

               val results = FilterResults()
               results.values = filteredList
               return results


           }

           override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
               submitList(filteredList)
           }
       }
    }

}