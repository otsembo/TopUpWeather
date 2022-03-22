package com.topupmama.weather.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.topupmama.weather.data.model.Favorites
import com.topupmama.weather.data.model.WeatherData

@Database(entities = [WeatherData::class, Favorites::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    companion object{

        @Volatile
        private var DB_INSTANCE:AppDatabase? = null

        fun getDBInstance(context:Context) : AppDatabase{
            synchronized(this){
                var instance = DB_INSTANCE

                if(instance ==  null){

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "weather_database"
                    ).fallbackToDestructiveMigration().build()

                }

                return instance
            }
        }

    }


}