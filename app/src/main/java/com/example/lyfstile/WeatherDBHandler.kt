package com.example.lyfstile

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import kotlinx.coroutines.runBlocking

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
abstract class WeatherDBHandler : RoomDatabase() {

    abstract fun dao(): WeatherDao

    companion object
    {
        @Volatile
        private var instance: WeatherDBHandler?= null

        fun getDatabaseClient(context: Context) : WeatherDBHandler {

            if (instance != null) return instance!!

            synchronized(this) {

                instance = Room
                    .databaseBuilder(context, WeatherDBHandler::class.java, "weather_db")
                    .fallbackToDestructiveMigration()
                    .build()
                return instance!!
            }
        }

    }
}





