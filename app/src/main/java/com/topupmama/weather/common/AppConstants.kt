package com.topupmama.weather.common

import java.lang.StringBuilder

object AppConstants {

    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    const val WEATHER_UNITS = "metric"

    val CITY_IDS = cityIds()

    private fun setUpCities() : HashMap<Int, String> {
        return hashMapOf<Int, String>(
                184742 to "Nairobi",
                186301 to "Mombasa",
                191245 to "Kisumu",
                184622 to "Nakuru",
                160263 to "Dar es Salaam",
                232422 to "Kampala",
                202061 to "Kigali",
                2267226 to "Lagos",
                2352778 to "Abuja",
                3369157 to "Cape Town",
                5128581 to "New York",
                5164352 to "London",
                2673722 to "Stockholm",
                4164138 to "Miami",
                6619279 to "Sydney",
                1816670 to "Beijing",
                2950158 to "Berlin",
                2968815 to "Paris",
                6458923 to "Lisbon",
                3117735 to "Madrid"
            )
    }

    private fun cityIds() : String{
        val builder = StringBuilder();
        for (i in setUpCities().keys){
            builder.append(i).append(',');
        }
        //remove final comma
        return builder.toString().substring(0, setUpCities().size - 1)

    }


}